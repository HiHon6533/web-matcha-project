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
import service.OrderService;
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
    if (calculatedHash.equalsIgnoreCase(vnp_SecureHash)) {
        String txnRef = request.getParameter("vnp_TxnRef");
        String vnpRespCode = request.getParameter("vnp_ResponseCode"); // "00" success

        if ("00".equals(vnpRespCode)) {
            boolean finalizeOk = new OrderService().finalizePaymentAfterVNPay(txnRef);
            // finalizeOk == true => đã trừ kho và cập nhật order/payment
            if (finalizeOk) {
                response.sendRedirect("payment_success.jsp?" + request.getQueryString());
            } else {
                // finalize failed (ví dụ: hết hàng khi finalizing)
                // cập nhật Payment/Order đã được DAO xử lý (đã set status thất bại) hoặc bạn có thể thêm log
                response.sendRedirect("confirm.jsp?reason=out_of_stock");
            }
        } else {
            // nếu trả về code khác -> mark payment thất bại
            // cập nhật Payment tương ứng (nếu cần) - hiện bạn đã làm merge trong PaymentReturnServlet
            response.sendRedirect("confirm.jsp");
        }
    } else {
        response.getWriter().println("<h3>Lỗi: Chữ ký không hợp lệ!</h3>");
    }

    }

}
