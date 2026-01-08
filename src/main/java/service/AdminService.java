package service;

import dao.OrderDAO;
import java.util.List;
import model.Order;


public class AdminService {
    private OrderDAO orderDAO = new OrderDAO();
    
    public List<Order> getAllOrders(){
        return orderDAO.getAllOrders();
    }
   
    public boolean updateStatus(Long orderId, String newStatus){
        return orderDAO.updateStatus(orderId, newStatus);
    }
}
