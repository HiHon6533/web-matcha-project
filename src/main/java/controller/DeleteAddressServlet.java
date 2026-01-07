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

@WebServlet(name = "DeleteAddressServlet", urlPatterns = {"/delete-address"})
public class DeleteAddressServlet extends HttpServlet {

    private AddressService addressService = new AddressService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            String idParam = request.getParameter("id");
            HttpSession session = request.getSession();
            Customer customer = (Customer) session.getAttribute("customer");

            if (idParam != null && customer != null) {
                Long addressId = Long.parseLong(idParam);
                
                addressService.deleteUserAddress(customer.getUserID(), addressId);
            }
            
            response.sendRedirect("user-address-list");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}