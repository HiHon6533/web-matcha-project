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
            
            // (Tuỳ chọn) Kiểm tra email null để tránh lỗi nếu session hết hạn
            if (email == null) {
                resp.sendRedirect("login.jsp");
                return;
            }

            // 1. Kiểm tra mật khẩu xác nhận
            if (!newPass.equals(confirmPass)) {
                req.setAttribute("error", "Mật khẩu xác nhận không khớp!");
                req.getRequestDispatcher("forgot-password-3.jsp").forward(req, resp);
                return;
            }

            AccountDAO dao = new AccountDAO();
            Account account = dao.findByEmail(email);

            if (account != null) {
                // 2. Hash mật khẩu và cập nhật thông tin
                String hashedPass = BCrypt.hashpw(newPass, BCrypt.gensalt(12));
                account.setPassword(hashedPass);
                
                account.setToken(null);
                account.setTokenExpiry(null);
                
                boolean isUpdated = dao.update(account);

                if (isUpdated) {
                    // 3. XỬ LÝ KHI THÀNH CÔNG (Đoạn này đã được sửa)
                    
                    // Xóa các attribute tạm của quy trình quên mật khẩu
                    session.removeAttribute("resetEmail");
                    session.removeAttribute("isVerified");
                    
                    // Lưu thông báo vào Session (để login.jsp hiển thị được)
                    // Lưu ý: Tên biến là "successMsg" để khớp với code trong login.jsp
                    session.setAttribute("successMsg", "Cập nhật mật khẩu thành công! Vui lòng đăng nhập.");
                    
                    // Chuyển hướng về trang Login (URL sẽ đổi thành login.jsp)
                    resp.sendRedirect("login.jsp");
                    
                } else {
                    // Xử lý khi lỗi update DB
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