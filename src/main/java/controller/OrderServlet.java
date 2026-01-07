package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;

import model.Customer;
import service.OrderService;

@WebServlet("/order")
public class OrderServlet extends HttpServlet{
    private OrderService orderService = new OrderService();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        Customer curCustomer = (Customer) session.getAttribute("CURRENT_USER");
        BigDecimal total = new BigDecimal(request.getParameter("amount"));
        boolean isSuccess = false;
        isSuccess = orderService.createOrder(curCustomer, total);
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
