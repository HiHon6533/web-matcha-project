package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import model.*;
import service.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{
    private AccountService accountService = new AccountService();
    private CustomerService customerService = new CustomerService();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Account account = accountService.login(email, password);
            
            //Xóa session cũ nếu có
            HttpSession oldSession = request.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }
            //Tạo session mới
            HttpSession session = request.getSession(true);
            session.setAttribute("LOGGED_IN_USER", account);
            
            if (account.getRole().equals("admin"))
            {
                response.sendRedirect(request.getContextPath() + "/admin");
            }
            else {
                Customer user = customerService.findCustomerByEmail(email);
                session.setAttribute("CURRENT_USER", user);
                response.sendRedirect(request.getContextPath() + "/home");
            }

            } catch (RuntimeException e) {
            request.setAttribute("loginError", e.getMessage());
            request.getRequestDispatcher("/login.jsp")
                   .forward(request, response);
        }
    }

}
