package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import model.*;
import service.CartService;
import service.AddressService; // giả sử có service quản lý địa chỉ
import java.util.List;

@WebServlet("/confirm")
public class ConfirmServlet extends HttpServlet {
    private CartService cartService = new CartService();
    private AddressService addressService = new AddressService(); // nếu chưa có, xem note bên dưới

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("CURRENT_USER") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        Customer curCustomer = (Customer) session.getAttribute("CURRENT_USER");
        Cart cart = cartService.findCartByUserId(curCustomer.getUserID());

        if (cart == null || cart.getProducts() == null || cart.getProducts().isEmpty()) {
            session.setAttribute("ERROR_MSG", "Giỏ hàng trống!");
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        // Lấy địa chỉ của khách (nếu bạn chưa có AddressService -> dùng session/DB để lấy)
        List<Address> addresses = addressService.getAddressesByUserId(curCustomer.getUserID()); // nếu ko có thì trả về empty list

        request.setAttribute("cart", cart);
        request.setAttribute("customer", curCustomer);
        request.setAttribute("addresses", addresses);
        request.setAttribute("amount", request.getParameter("amount"));
        request.setAttribute("orderInfo", request.getParameter("orderInfo"));
        request.getRequestDispatcher("/confirm.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Có thể redirect về cart
        response.sendRedirect(request.getContextPath() + "/cart");
    }
}
