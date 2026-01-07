package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.*;
import service.OrderService;

@WebServlet("/history")
public class HistoryServlet extends HttpServlet{
    private OrderService orderService = new OrderService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("LOGGED_IN_USER") == null){
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        Customer user = (Customer) session.getAttribute("CURRENT_USER");
        List<Order> listOrders = orderService.getAllOrdersByUserId(user.getUserID());
        request.setAttribute("historyOrders", listOrders);
        request.getRequestDispatcher("/history.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
