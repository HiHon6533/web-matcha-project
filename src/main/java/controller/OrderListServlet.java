package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import service.AdminService;
import model.Order;

@WebServlet("/orders")
public class OrderListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final AdminService adminService = new AdminService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Order> orders = null;
        try {
            orders = adminService.getAllOrders();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (orders == null) {
            orders = new ArrayList<>();
        }

        // Format ngày ở server để tránh lỗi fmt:formatDate với LocalDateTime trong JSP
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        List<Map<String, Object>> ordersView = new ArrayList<>();
        for (Order o : orders) {
            try {
                Map<String, Object> m = new HashMap<>();
                m.put("orderID", o.getOrderID());
                m.put("customer", o.getCustomer()); // giữ nguyên để JSP truy cập fullName, phoneNumber
                if (o.getCreatedAt() != null) {
                    m.put("createdAtStr", o.getCreatedAt().format(formatter));
                } else {
                    m.put("createdAtStr", "");
                }
                m.put("total", o.getTotal());
                m.put("orderStatus", o.getOrderStatus());
                // giữ products nếu JSP cần lặp chi tiết món
                try {
                    m.put("products", o.getProducts());
                } catch (Throwable t) {
                    // nếu Order không có getProducts(), bỏ qua
                }
                ordersView.add(m);
            } catch (Exception ex) {
                ex.printStackTrace(); // không để 1 order lỗi crash toàn bộ trang
            }
        }

        // Đẩy view list (đã format) cho JSP
        req.setAttribute("orders", ordersView);

        // Forward tới orders.jsp
        req.getRequestDispatcher("/orders.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
