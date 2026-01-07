package controller;

import service.AccountService;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet 
{
    
    private AccountService accountService = new AccountService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        request.setCharacterEncoding("UTF-8");
        
        // Lấy dữ liệu
        String fullname = request.getParameter("fullname");
        String phone = request.getParameter("phonenumber");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String result = accountService.registerUser(fullname, phone, email, password);

        if ("Success".equals(result)) 
        {
            request.setAttribute("registerSuccess", true);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else 
        {
            request.setAttribute("error", result);
            request.setAttribute("fullname", fullname);
            request.setAttribute("phonenumber", phone);
            request.setAttribute("email", email);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}