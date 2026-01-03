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

@WebServlet(name = "SetDefaultAddressControl", urlPatterns = {"/set-default-address"})
public class SetDefaultAddressServlet extends HttpServlet
{
    private AddressService addressService = new AddressService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        String idParam = request.getParameter("id");
        Long addressId = Long.parseLong(idParam);
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        addressService.changeDefaultAddress(customer.getUserID(), addressId);
        response.sendRedirect("ten_cho_doi_diachi");
    }
}