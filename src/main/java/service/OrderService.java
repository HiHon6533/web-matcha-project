package service;

import java.math.BigDecimal;
import java.util.List;

import dao.CartDAO;
import dao.OrderDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.Customer;
import model.Order;
import model.Product;

public class OrderService {
    private OrderDAO orderDAO = new OrderDAO();
    private CartDAO cartDAO = new CartDAO();
    
    public List<Order> getAllOrdersByUserId(Long userId){
        return orderDAO.findByUserId(userId);
    }
    
    public boolean createOrder(Customer cus, BigDecimal total){
        Cart cart = cartDAO.findCartByUserId(cus.getUserID());
        if (cart != null){
            orderDAO.checkout(cus, cart, total);
            return true;
        }
        return false;
    }
    
    public boolean createOrderWithTransaction(HttpServletRequest request, BigDecimal total, String note, String addressIdStr, String txnRef) {
        HttpSession session = request.getSession(false);
        Customer cus = (Customer) session.getAttribute("CURRENT_USER");
        Cart cart = cartDAO.findCartByUserId(cus.getUserID());
        
        if (cart == null) return false;
        return orderDAO.checkoutWithTxn(cus, cart, total, note, addressIdStr, txnRef);
        
    }
    
}
