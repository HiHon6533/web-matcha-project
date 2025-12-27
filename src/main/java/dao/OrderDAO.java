package dao;

import model.Order;
import java.util.List;

public class OrderDAO extends GenericDAO<Order, Long> {

    public OrderDAO() {
        super(Order.class);
    }

    public List<Order> findByCustomer(Long userId) {
        return em.createQuery(
                "SELECT o FROM Order o WHERE o.customer.userID = :id",
                Order.class)
                .setParameter("id", userId)
                .getResultList();
    }
}
