package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList; // Nhớ import ArrayList

// Import thêm các Model và Service cần thiết
import model.Account;
import model.Order;
import model.Matcha; // Import Matcha
import model.Milk;   // Import Milk
import service.AdminService;
import service.DrinkService; // Import DrinkService

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    
    // Khai báo cả 2 Service
    AdminService adminService = new AdminService();
    DrinkService drinkService = new DrinkService(); // Thêm cái này để lấy Matcha/Milk

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        
        // 1. Kiểm tra đăng nhập (Giữ nguyên)
        if (session == null || session.getAttribute("LOGGED_IN_USER") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        Account account = (Account) session.getAttribute("LOGGED_IN_USER");
        if (!"admin".equals(account.getRole())) {
             resp.sendError(HttpServletResponse.SC_FORBIDDEN, "User không có quyền truy cập!");
             return;
        }

        // 2. Lấy danh sách đơn hàng (Giữ nguyên)
        List<Order> listOrders = adminService.getAllOrders();
        req.setAttribute("orders", listOrders);
        
        // 3. THÊM ĐOẠN NÀY: Lấy Matcha và Milk (giống bên HomeServlet)
        List<Matcha> listMatcha = new ArrayList<>();
        List<Milk> listMilk = new ArrayList<>();
        
        try {
            // Gọi lại hàm từ DrinkService (Tái sử dụng code)
            listMatcha = drinkService.getAllMatchaTypes();
            listMilk = drinkService.getAllMilkTypes();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 4. Đẩy dữ liệu sang admin.jsp
        req.setAttribute("listMatcha", listMatcha); 
        req.setAttribute("listMilk", listMilk);

        // 5. Forward
        req.getRequestDispatcher("/admin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}