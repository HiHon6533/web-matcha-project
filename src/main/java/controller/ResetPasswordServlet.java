package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;

import dao.AccountDAO;
import model.Account;

@WebServlet("/reset-password")
public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String newPass = req.getParameter("newPassword");
            String confirmPass = req.getParameter("confirmPassword");
            
            HttpSession session = req.getSession();
            String email = (String) session.getAttribute("resetEmail");
            Boolean isVerified = (Boolean) session.getAttribute("isVerified");


            if (!newPass.equals(confirmPass)) {
                req.setAttribute("error", "Mật khẩu xác nhận không khớp!");
                req.getRequestDispatcher("forgot-password-3.jsp").forward(req, resp);
                return;
            }

            AccountDAO dao = new AccountDAO();
            Account account = dao.findByEmail(email);

            if (account != null) {
                String hashedPass = BCrypt.hashpw(newPass, BCrypt.gensalt(12));
                account.setPassword(hashedPass);
                
                account.setToken(null);
                account.setTokenExpiry(null);
                
                boolean isUpdated = dao.update(account);

                if (isUpdated) {
                    session.removeAttribute("resetEmail");
                    session.removeAttribute("isVerified");
                    
                    req.setAttribute("message", "Cập nhật mật khẩu thành công! Vui lòng đăng nhập.");
                    req.getRequestDispatcher("login.jsp").forward(req, resp);
                } else {
                    req.setAttribute("error", "Lỗi cập nhật mật khẩu.");
                    req.getRequestDispatcher("forgot-password-3.jsp").forward(req, resp);
                }
            } 

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi hệ thống!");
            req.getRequestDispatcher("forgot-password-1.jsp").forward(req, resp);
        }
    }
}