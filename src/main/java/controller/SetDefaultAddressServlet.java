package controller;

import model.Customer;
import service.AddressService;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "SetDefaultAddressServlet", urlPatterns = {"/set-default-address"})
public class SetDefaultAddressServlet extends HttpServlet {
    
    private AddressService addressService = new AddressService();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        try {
            // 1. Lấy tham số "addressId" (khớp với name trong form ở profile.jsp)
            String idParam = request.getParameter("addressId");
            
            if (idParam != null && !idParam.trim().isEmpty()) {
                Long addressId = Long.parseLong(idParam);
                
                HttpSession session = request.getSession();
                
                // 2. Lấy object từ Session (Dùng key CURRENT_USER cho đồng bộ)
                Customer customer = (Customer) session.getAttribute("CURRENT_USER");
                
                if (customer != null) {
                    // Gọi service thực hiện logic đổi địa chỉ mặc định
                    addressService.changeDefaultAddress(customer.getUserID(), addressId);
                }
            }
            
            // 3. Chuyển hướng về lại trang Profile (Servlet)
            response.sendRedirect("profile");
            
        } catch (NumberFormatException e) {
            // Nếu ID không hợp lệ, cũng quay về trang profile
            response.sendRedirect("profile");
        } catch (Exception e) {
            e.printStackTrace();
            // Nếu có lỗi hệ thống, tạm thời quay về profile hoặc trang lỗi
            response.sendRedirect("profile"); 
        }
    }
}