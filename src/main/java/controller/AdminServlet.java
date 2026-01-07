package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import model.Account;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        //Chưa đăng nhập -> return về login
        if (session == null || session.getAttribute("LOGGED_IN_USER") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        Account account = (Account) session.getAttribute("LOGGED_IN_USER");
        if (!"admin".equals(account.getRole())) {
             // Kiểm tra lại role
             resp.sendError(HttpServletResponse.SC_FORBIDDEN, "User không có quyền truy cập!");
             return;
        }
        req.getRequestDispatcher("/cart.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {

        doGet(req, resp);
    }
}
