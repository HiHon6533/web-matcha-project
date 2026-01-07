package controller;

import dao.AddressDAO;
import model.Address;
import model.Customer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/profile") // Đường dẫn mới để vào trang cá nhân
public class ProfileServlet extends HttpServlet {
    
    private AddressDAO addressDAO = new AddressDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Customer customer = (Customer) session.getAttribute("CURRENT_USER");

        if (customer == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        // 1. Lấy danh sách địa chỉ mới nhất từ DB
        List<Address> listAddress = addressDAO.getAllAddressByUserId(customer.getUserID());
        
        // 2. Gửi sang JSP
        req.setAttribute("listAddress", listAddress);
        
        // 3. Chuyển hướng đến file giao diện
        req.getRequestDispatcher("profile.jsp").forward(req, resp);
    }
}