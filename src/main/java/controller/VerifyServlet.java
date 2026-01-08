package controller;

import service.AccountService;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession; // Thêm import này

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
            // SỬA: Dùng Session để gửi thông báo "thành công" sang trang login.jsp
            // Tên biến "successMsg" phải khớp với code trong login.jsp
            HttpSession session = request.getSession();
            session.setAttribute("successMsg", "Xác thực thành công! Bạn có thể đăng nhập ngay.");
            
            // SỬA: Dùng sendRedirect để URL chuyển về login.jsp (xóa token khỏi thanh địa chỉ)
            response.sendRedirect("login.jsp");
        }
        else {
            // SỬA: Đổi tên biến thành "loginError" để khớp với phần hiển thị lỗi trong login.jsp
            request.setAttribute("loginError", "Liên kết xác thực không hợp lệ hoặc đã hết hạn!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}