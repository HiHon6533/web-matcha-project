package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import model.Account;
import service.AccountService;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{
    private AccountService accountService = new AccountService();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Account account = accountService.login(email, password);

            // Lưu thông tin đăng nhập vào session
            //HttpSession session = request.getSession();
            //session.setAttribute("LOGGED_IN_USER", account);

            response.sendRedirect(request.getContextPath() + "/home");

        } catch (RuntimeException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/login.jsp")
                   .forward(request, response);
        }
    }

}
