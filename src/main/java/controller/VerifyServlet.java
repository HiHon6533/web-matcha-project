package controller;

import service.AccountService;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "VerifyServlet", urlPatterns = {"/verify"})
public class VerifyServlet extends HttpServlet {

    private AccountService accountService = new AccountService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String token = request.getParameter("token");
        
        boolean verified = false;
        if (token != null && !token.isEmpty()) {
            verified = accountService.verifyAccount(token);
        }

        if (verified) {
            request.setAttribute("message", "Xác thực thành công! Bạn có thể đăng nhập ngay.");
            request.setAttribute("messageType", "success");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
        else {
            request.setAttribute("message", "Liên kết xác thực không hợp lệ hoặc đã hết hạn!");
            request.setAttribute("messageType", "error");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}