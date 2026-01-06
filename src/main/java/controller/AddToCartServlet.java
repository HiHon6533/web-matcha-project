package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import model.Account;
import model.Customer;
import service.CartService;

@WebServlet("/addToCart")
public class AddToCartServlet extends HttpServlet {

    private CartService cartService = new CartService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Kiểm tra đăng nhập
        HttpSession session = request.getSession();
        Customer currentUser = (Customer) session.getAttribute("CURRENT_USER");
        
        if (currentUser == null) {
            // Chưa đăng nhập -> Chuyển về trang login
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        Long userId = currentUser.getUserID();

        // 2. Lấy loại sản phẩm thêm vào
        String type = request.getParameter("type"); // "standard" hoặc "custom"
        String quantityStr = request.getParameter("quantity");
        int quantity = (quantityStr == null || quantityStr.isEmpty()) ? 1 : Integer.parseInt(quantityStr);
        
        boolean isSuccess = false;

        // 3. Phân luồng xử lý
        if ("standard".equals(type)) {
            // Lấy ID trực tiếp từ hidden field
            Long productId = Long.parseLong(request.getParameter("productId"));
            isSuccess = cartService.addStandardProduct(userId, productId, quantity);
            
        } else if ("custom".equals(type)) {
            // Lấy Component ID từ hidden field (do JS set vào)
            Long matchaId = Long.parseLong(request.getParameter("matchaId"));
            Long milkId = Long.parseLong(request.getParameter("milkId"));
            String size = request.getParameter("size");
            
            isSuccess = cartService.addCustomDrink(userId, matchaId, milkId, size, quantity);
        }

        // 4. Phản hồi
        if (isSuccess) {
            // Thành công -> Về trang giỏ hàng
            response.sendRedirect(request.getContextPath() + "/cart");
        } else {
            // Thất bại
            // Lưu thông báo lỗi vào session hoặc request để hiển thị
            session.setAttribute("ERROR_MSG", "Sản phẩm hoặc công thức đồ uống không tồn tại!");
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }
}

