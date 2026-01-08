package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Lấy session hiện tại. 
        // Tham số 'false' nghĩa là: nếu chưa có session thì đừng tạo mới (vì đang muốn xóa mà).
        HttpSession session = request.getSession(false);

        // 2. Kiểm tra và hủy session
        if (session != null) {
            // Lệnh này sẽ xóa toàn bộ dữ liệu trong session (LOGGED_IN_USER, CURRENT_USER,...)
            // và hủy hiệu lực của session ID.
            session.invalidate(); 
        }

        // 3. Chuyển hướng về trang Home
        // request.getContextPath() giúp lấy đúng đường dẫn gốc của dự án
        response.sendRedirect(request.getContextPath() + "/home");
    }
}