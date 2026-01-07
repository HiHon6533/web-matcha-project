package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import service.OrderService;
import util.VNPayConfig;

@WebServlet("/confirm-action")
public class ConfirmActionServlet extends HttpServlet {
    private OrderService orderService = new OrderService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("CURRENT_USER") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String amountParam = request.getParameter("amount");
        BigDecimal total = new BigDecimal(amountParam == null ? "0" : amountParam);
        String orderInfo = request.getParameter("orderInfo");
        String note = request.getParameter("note");
        String addressIdStr = request.getParameter("addressId"); // nếu bạn dùng địa chỉ
        // Sinh txnRef
        String vnp_TxnRef = String.valueOf(System.currentTimeMillis());

        
        
        // Tạo order + payment (payment.transactionId = vnp_TxnRef, status = "Chờ thanh toán")
        boolean created = orderService.createOrderWithTransaction(request, total, note, addressIdStr, vnp_TxnRef);
        if (!created) {
            session.setAttribute("ERROR_MSG", "Tạo đơn hàng thất bại. Vui lòng thử lại.");
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        // Tạo URL VNPAY (sao chép logic từ PaymentServlet nhưng dùng vnp_TxnRef đã sinh)
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
        long amount = total.multiply(BigDecimal.valueOf(100)).longValue(); // VNPay expects amount *100

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", orderInfo);
        vnp_Params.put("vnp_OrderType", "topup");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_IpAddr", request.getRemoteAddr());
        
        String returnUrl = request.getScheme() + "://"
                + request.getServerName() + ":"
                + request.getServerPort()
                + request.getContextPath()
                + "/payment-return";

        vnp_Params.put("vnp_ReturnUrl", returnUrl);


        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString())).append('=')
                     .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.vnp_HashSecret, hashData.toString());
        String queryUrl = query.toString();
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;
        response.sendRedirect(paymentUrl);
    }
}
