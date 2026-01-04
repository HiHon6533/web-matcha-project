/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
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
