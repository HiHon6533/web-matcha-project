package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

import dao.AccountDAO;
import model.Account;
import service.EmailService;
import util.TokenUtil;

@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("forgot-password-1.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String email = req.getParameter("email");
            
            AccountDAO dao = new AccountDAO();
            Account acc = dao.findByEmail(email);

            if (acc == null) {
                req.setAttribute("error", "Email này chưa được đăng ký!");
                req.getRequestDispatcher("forgot-password-1.jsp").forward(req, resp);
                return;
            }

            if (acc.getActived() == null || !acc.getActived()) { 
                req.setAttribute("error", "Tài khoản chưa được kích hoạt!");
                req.getRequestDispatcher("forgot-password-1.jsp").forward(req, resp);
                return;
            }

            String token = TokenUtil.tokenPass(); 
            LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(10);

            acc.setToken(token);
            acc.setTokenExpiry(expiryTime);

            boolean isUpdated = dao.update(acc);

            if (isUpdated) {
                EmailService emailService = new EmailService();
                
                boolean isSent = emailService.sendChangePasswordEmail(email, acc.getEmail(), token);

                if (isSent) {
                    HttpSession session = req.getSession();
                    session.setAttribute("resetEmail", email);
                    
                    resp.sendRedirect("forgot-password-2.jsp");
                } else {
                    req.setAttribute("error", "Lỗi gửi email! Vui lòng kiểm tra lại mạng.");
                    req.getRequestDispatcher("forgot-password-1.jsp").forward(req, resp);
                }
            } 

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Đã có lỗi xảy ra!");
            req.getRequestDispatcher("forgot-password-1.jsp").forward(req, resp);
        }
    }
}