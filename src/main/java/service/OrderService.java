package service;

import java.math.BigDecimal;
import java.util.List;

import dao.CartDAO;
import dao.OrderDAO;
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
}
