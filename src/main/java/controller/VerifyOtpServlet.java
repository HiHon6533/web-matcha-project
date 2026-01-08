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

@WebServlet("/verify-otp")
public class VerifyOtpServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String userOtp = req.getParameter("otp");
            HttpSession session = req.getSession();
            String email = (String) session.getAttribute("resetEmail");

            if (email == null) {
                resp.sendRedirect("forgot-password-1.jsp");
                return;
            }
            AccountDAO dao = new AccountDAO();
            Account account = dao.findByEmail(email);

            String dbToken = account.getToken();
            LocalDateTime dbExpiry = account.getTokenExpiry();

            if (dbToken == null || dbExpiry == null) {
                req.setAttribute("error", "Yêu cầu không hợp lệ!");
                req.getRequestDispatcher("forgot-password-2.jsp").forward(req, resp);
                return;
            }

            if (!dbToken.equals(userOtp)) {
                req.setAttribute("error", "Mã xác thực không chính xác!");
                req.getRequestDispatcher("forgot-password-2.jsp").forward(req, resp);
                return;
            }

            if (LocalDateTime.now().isAfter(dbExpiry)) {
                req.setAttribute("error", "Mã OTP đã hết hạn! Vui lòng thực hiện lại từ đầu.");
                req.getRequestDispatcher("forgot-password-1.jsp").forward(req, resp);
                return;
            }

            session.setAttribute("isVerified", true);  
            resp.sendRedirect("forgot-password-3.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi hệ thống!");
            req.getRequestDispatcher("forgot-password-1.jsp").forward(req, resp);
        }
    }
}