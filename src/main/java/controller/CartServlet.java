package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import model.*;
import service.CartService;

@WebServlet("/cart")
public class CartServlet extends HttpServlet{
    private CartService cartService = new CartService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("LOGGED_IN_USER") == null){
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        Account account = (Account) session.getAttribute("LOGGED_IN_USER");
        Long userId = account.getCustomer().getUserID();
        Cart cart = cartService.getCartByUserID(userId);
        request.setAttribute("cart", cart);
        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }
}
