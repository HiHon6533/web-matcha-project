/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import service.OrderService;

/**
 *
 * @author ADMIN
 */
@WebServlet("/payment-cancel")
public class PaymentCancelServlet extends HttpServlet {
    private OrderService orderService = new OrderService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String txnRef = request.getParameter("txnRef");
        if (txnRef != null && !txnRef.isEmpty()) {
            boolean ok = orderService.markPaymentFailed(txnRef, "User cancelled");
            if (ok) {
                // clear session PENDING keys
                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.removeAttribute("PENDING_TXN");
                    session.removeAttribute("PENDING_CART");
                    session.removeAttribute("PENDING_AMOUNT");
                    session.removeAttribute("PENDING_ORDERINFO");
                }
            }
        }
        response.sendRedirect(request.getContextPath() + "/cart");
    }
}

