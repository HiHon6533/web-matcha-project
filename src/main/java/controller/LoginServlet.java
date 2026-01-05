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
            Customer user = customerService.findCustomerByEmail(email);
            HttpSession session = request.getSession();
            session.setAttribute("LOGGED_IN_USER", account);
            session.setAttribute("CURRENT_USER", user);
            
            if (account.getRole().equals("admin"))
            {
                request.getRequestDispatcher("/admin").forward(request, response);
            }
            else {
                request.getRequestDispatcher("/home").forward(request, response);
            }

            } catch (RuntimeException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/login.jsp")
                   .forward(request, response);
        }
    }

}
