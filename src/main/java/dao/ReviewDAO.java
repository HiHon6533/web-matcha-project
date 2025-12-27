package dao;

import model.Review;
import java.util.List;

public class ReviewDAO extends GenericDAO<Review, Long> {

    public ReviewDAO() {
        super(Review.class);
    }

    public List<Review> findByOrder(Long orderId) {
        return em.createQuery(
                "SELECT r FROM Review r WHERE r.order.orderID = :id",
                Review.class)
                .setParameter("id", orderId)
                .getResultList();
    }
}
