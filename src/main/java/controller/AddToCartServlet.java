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
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("LOGGED_IN_USER") == null)
        {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        Customer currentUser = (Customer) session.getAttribute("CURRENT_USER");

        Long userId = currentUser.getUserID();

        // Lấy loại sản phẩm thêm vào
        String type = request.getParameter("type"); // "standard" hoặc "custom"
        String quantityStr = request.getParameter("quantity");
        int quantity = (quantityStr == null || quantityStr.isEmpty()) ? 1 : Integer.parseInt(quantityStr);
        
        boolean isSuccess = false;

        //Phân luồng xử lý
        if ("standard".equals(type)) {
            Long productId = Long.parseLong(request.getParameter("productId"));
            isSuccess = cartService.addStandardProduct(userId, productId, quantity);
            
        } else if ("custom".equals(type)) {
            Long matchaId = Long.parseLong(request.getParameter("matchaId"));
            Long milkId = Long.parseLong(request.getParameter("milkId"));
            String size = request.getParameter("size");
            
            isSuccess = cartService.addCustomDrink(userId, matchaId, milkId, size, quantity);
        }

        //Phản hồi
        if (isSuccess) {
            // Thành công -> Về trang giỏ hàng
            response.sendRedirect(request.getContextPath() + "/cart");
        } else {
            // Thất bại
            request.setAttribute("ERROR_MSG", "Sản phẩm hoặc công thức đồ uống không tồn tại!");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
}

