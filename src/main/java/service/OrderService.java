package service;

import dao.OrderDAO;
import java.util.List;
import model.Order;

public class OrderService {
    private OrderDAO orderDAO = new OrderDAO();
    public List<Order> getAllOrdersByUserId(Long userId){
        return orderDAO.findByUserId(userId);
    }
}
