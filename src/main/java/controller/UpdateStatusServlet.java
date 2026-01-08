package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import service.AdminService;


@WebServlet("/updateStatus")
public class UpdateStatusServlet extends HttpServlet {
    private AdminService adminService = new AdminService();
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Lấy mã đơn hàng
        String orderIdStr = request.getParameter("orderId");
        Long orderId = Long.parseLong(orderIdStr);
        // Lấy status
        String newStatus = request.getParameter("status"); 
        
        boolean isSuccess = adminService.updateStatus(orderId, newStatus);
        
        //Thất bại
        if (!isSuccess){
            request.setAttribute("ERROR_MSG", "Cập nhật thất bại!");
            request.getRequestDispatcher("/admin").forward(request, response);
        }
        else {
            response.sendRedirect(request.getContextPath() + "/admin");
        }
       
    }
}
