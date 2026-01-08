/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import util.VNPayConfig;
import util.JPAUtil;
import service.OrderService;
import service.EmailService;
import model.Order;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author ADMIN
 */
@WebServlet("/payment-return")
public class PaymentReturnServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        // Lấy SecureHash từ VNPay gửi về
        String vnp_SecureHash = request.getParameter("vnp_SecureHash");

        // Lấy toàn bộ params
        Map<String, String> vnp_Params = new HashMap<>();
        for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();) {
            String fieldName = params.nextElement();
            String fieldValue = request.getParameter(fieldName);
            if (fieldValue != null && fieldValue.length() > 0) {
                vnp_Params.put(fieldName, fieldValue);
            }
        }

        // Xóa 2 field không dùng hash
        vnp_Params.remove("vnp_SecureHash");
        vnp_Params.remove("vnp_SecureHashType");

        // SORT KEY
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);

        // BUILD HASH DATA (PHẢI URL ENCODE)
        StringBuilder hashData = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);

            hashData.append(fieldName)
                    .append('=')
                    .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

            if (itr.hasNext()) {
                hashData.append('&');
            }
        }

        // Tính lại chữ ký
        String calculatedHash = VNPayConfig.hmacSHA512(
                VNPayConfig.vnp_HashSecret,
                hashData.toString()
        );

        // SO SÁNH
        if (calculatedHash.equalsIgnoreCase(vnp_SecureHash)) {
            String txnRef = request.getParameter("vnp_TxnRef");
            String vnpRespCode = request.getParameter("vnp_ResponseCode");
            OrderService orderService = new OrderService();

            if ("00".equals(vnpRespCode)) {
                boolean finalizeOk = orderService.finalizePaymentAfterVNPay(txnRef);
                if (finalizeOk) {
                    // --- Gửi email xác nhận đơn hàng (không làm gián đoạn flow nếu gửi mail lỗi) ---
                    try {
                        Order order = fetchOrderByTxnRef(txnRef);
                        if (order != null && order.getCustomer() != null && order.getCustomer().getAccount().getEmail() != null) {
                            String customerEmail = order.getCustomer().getAccount().getEmail();
                            EmailService emailService = new EmailService();
                            boolean mailSent = emailService.sendOrderConfirmation(customerEmail, order);
                            if (!mailSent) {
                                // Log nhưng không throw
                                getServletContext().log("Warning: Gửi email xác nhận đơn hàng thất bại cho txnRef=" + txnRef);
                            } else {
                                getServletContext().log("Email xác nhận đơn hàng đã gửi tới " + customerEmail + " cho txnRef=" + txnRef);
                            }
                        } else {
                            getServletContext().log("Thông tin order/customer/email không đầy đủ để gửi mail cho txnRef=" + txnRef);
                        }
                    } catch (Exception e) {
                        // Bắt mọi lỗi gửi mail/nhận order để không ảnh hưởng tới user flow
                        getServletContext().log("Lỗi khi cố gắng gửi email xác nhận cho txnRef=" + txnRef, e);
                    }

                    // Optionally clear pending session attributes (an toàn, không thay đổi logic thanh toán)
                    HttpSession session = request.getSession(false);
                    if (session != null) {
                        session.removeAttribute("PENDING_TXN");
                        session.removeAttribute("PENDING_CART");
                        session.removeAttribute("PENDING_AMOUNT");
                        session.removeAttribute("PENDING_ORDERINFO");
                        session.removeAttribute("PENDING_CUSTOMER");
                        session.removeAttribute("PENDING_ADDRESSES");
                    }

                    response.sendRedirect("payment_success.jsp?" + request.getQueryString());
                } else {
                    // finalize failed -> mark payment failed
                    orderService.markPaymentFailed(txnRef, "Finalize failed or out of stock");
                    response.sendRedirect("confirm.jsp?reason=out_of_stock");
                }
            } else {
                // Payment not successful -> mark failed
                orderService.markPaymentFailed(txnRef, "vnpRespCode=" + vnpRespCode);
                response.sendRedirect("confirm.jsp?" + request.getQueryString());
            }
        } else {
            response.getWriter().println("<h3>Lỗi: Chữ ký không hợp lệ!</h3>");
        }
    }

    /**
     * Helper: lấy Order từ txnRef trực tiếp bằng JPA để dùng cho gửi email.
     * Query dùng JOIN FETCH để đảm bảo fetch product lines + product nếu mapping lazy.
     */
    private Order fetchOrderByTxnRef(String txnRef) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // JPQL: chọn Order có Payment.transactionId = :txRef
            // và fetch products + product để tránh LazyInitializationException trong email template.
            // NOTE: điều chỉnh tên thuộc tính nếu entity của bạn dùng tên khác.
            return em.createQuery(
                    "SELECT DISTINCT o FROM Order o " +
                    "JOIN o.payment p " +
                    "LEFT JOIN FETCH o.products pl " +
                    "LEFT JOIN FETCH pl.product prod " +
                    "WHERE p.transactionId = :txRef", Order.class)
                    .setParameter("txRef", txnRef)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            getServletContext().log("Lỗi khi fetch Order theo txnRef=" + txnRef, e);
            return null;
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }
}
