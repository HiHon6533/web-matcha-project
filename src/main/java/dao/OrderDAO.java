package dao;

import jakarta.persistence.EntityManager;
import model.Order;
import java.util.List;
import util.JPAUtil;

public class OrderDAO {

    //Lấy danh sách Order của User
    public List<Order> findByUserId(Long userId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                "SELECT o FROM Order o WHERE o.customer.userID = :id",
                Order.class)
                .setParameter("id", userId)
                .getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
}
