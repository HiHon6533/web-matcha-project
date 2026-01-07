/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import util.VNPayConfig;
/**
 *
 * @author ADMIN
 */
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import model.Payment;
import util.JPAUtil;

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
        // tại phần "SO SÁNH" nếu hash hợp lệ:
    if (calculatedHash.equalsIgnoreCase(vnp_SecureHash)) {
        String txnRef = request.getParameter("vnp_TxnRef");
        String vnpRespCode = request.getParameter("vnp_ResponseCode"); // 00 = success
        String transactionStatus = request.getParameter("vnp_TransactionStatus"); // hoặc dùng vnp_ResponseCode

        // Update payment/order in DB
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            // Tìm payment theo transactionId
            List<Payment> payments = em.createQuery("SELECT p FROM Payment p WHERE p.transactionId = :txRef", Payment.class)
                .setParameter("txRef", txnRef)
                .getResultList();
            if (!payments.isEmpty()) {
                Payment p = payments.get(0);
                if ("00".equals(vnpRespCode) || "00".equals(request.getParameter("vnp_TransactionStatus"))) {
                    p.setStatus("Đã thanh toán");
                    p.getOrder().setOrderStatus("Đã thanh toán!");
                } else {
                    p.setStatus("Thanh toán thất bại: " + vnpRespCode);
                    p.getOrder().setOrderStatus("Thanh toán thất bại");
                    // nếu muốn: hoàn tác tồn kho hoặc trả hàng lại vào cart -> implement thêm
                }
                em.merge(p);
                em.merge(p.getOrder());
            }
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) tx.rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }

        if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
            response.sendRedirect("payment_success.jsp?" + request.getQueryString());
        } else {
            response.sendRedirect("payment_failure.jsp");
        }
    } else {
        response.getWriter().println("<h3>Lỗi: Chữ ký không hợp lệ!</h3>");
    }

    }

}
