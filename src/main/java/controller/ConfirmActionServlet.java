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
import model.Cart;
import model.Customer;
import service.CartService;
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

        // CHECK tồn kho
        Customer cus = (Customer) session.getAttribute("CURRENT_USER");
        Cart cart = new CartService().findCartByUserId(cus.getUserID());
        if (cart == null || !new OrderService().isStockAvailableForCart(cart)) {
            session.setAttribute("ERROR_MSG", "Không đủ hàng trong kho. Vui lòng chỉnh sửa giỏ hàng.");
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        // Tạo order + payment (payment.transactionId = vnp_TxnRef, status = "Chờ thanh toán")
        boolean created = orderService.createOrderWithTransaction(request, total, note, addressIdStr, vnp_TxnRef);

        if (!created) {
            session.setAttribute("ERROR_MSG", "Tạo đơn hàng thất bại. Vui lòng thử lại.");
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        if (created) {
            session.setAttribute("PENDING_TXN", vnp_TxnRef);
        }

        // Tạo URL VNPAY
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
        // store raw orderInfo (no encode here) — we'll encode only when building hash/query
        vnp_Params.put("vnp_OrderInfo", orderInfo == null ? "" : orderInfo);
        vnp_Params.put("vnp_OrderType", "topup");
        vnp_Params.put("vnp_Locale", "vn");

        // Lấy IP đúng (ưu tiên X-Forwarded-For)
        String ipAddr = request.getHeader("X-Forwarded-For");
        if (ipAddr != null && !ipAddr.isEmpty()) {
            ipAddr = ipAddr.split(",")[0].trim();
        } else {
            ipAddr = request.getRemoteAddr();
        }
        vnp_Params.put("vnp_IpAddr", ipAddr);

        // Use fixed public return URL from config (no internal port)
        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl);

        // CreateDate & ExpireDate with proper timezone
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));

        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        // Build hashData and query using URLEncoder.encode(value, "UTF-8")
        // IMPORTANT: do NOT modify '+' to '%20' — use URLEncoder's default behavior.
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && fieldValue.length() > 0) {
                // Encode value using URLEncoder (UTF-8). VNPAY sample uses this encoding (spaces -> '+').
                String encodedValue = URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString());

                // Use the encoded value for both hashData and query (keys left as plain field names)
                hashData.append(fieldName).append("=").append(encodedValue);
                query.append(fieldName).append("=").append(encodedValue);

                if (itr.hasNext()) {
                    hashData.append("&");
                    query.append("&");
                }
            }
        }

        // Compute secure hash (HMAC SHA512)
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.vnp_HashSecret, hashData.toString());

        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + query.toString() + "&vnp_SecureHash=" + vnp_SecureHash;

        // Logs for debugging (you can remove later)
        getServletContext().log("VNPay createDate=" + vnp_CreateDate + ", expireDate=" + vnp_ExpireDate);
        getServletContext().log("VNPay hashData=" + hashData.toString());
        getServletContext().log("VNPay paymentUrl=" + paymentUrl);

        // Redirect to VNPay
        response.sendRedirect(paymentUrl);
    }
}
