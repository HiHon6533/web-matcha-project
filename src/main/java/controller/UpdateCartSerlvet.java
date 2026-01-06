package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import model.*;
import service.CartService;

@WebServlet("/updateCart")
public class UpdateCartSerlvet extends HttpServlet {
    private CartService cartService = new CartService();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();        
        Customer user = (Customer) session.getAttribute("CURRENT_USER"); 

        String action = request.getParameter("action"); // "increase", "decrease", "remove"
        String lineIdStr = request.getParameter("lineId");
        
        if (lineIdStr != null && action != null) {
            try {
                long lineId = Long.parseLong(lineIdStr);
                if ("increase".equals(action)) cartService.increaseQuantity(lineId);
                else if ("decrease".equals(action)) cartService.decreaseQuantity(lineId);
                else if ("remove".equals(action)) cartService.removeLine(lineId);

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        response.sendRedirect(request.getContextPath() + "/cart");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
