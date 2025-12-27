package dao;

import model.Cart;

public class CartDAO extends GenericDAO<Cart, Long> {

    public CartDAO() {
        super(Cart.class);
    }

    public Cart findByCustomerId(Long userId) {
        return em.createQuery(
                "SELECT c FROM Cart c WHERE c.customer.userID = :id",
                Cart.class)
                .setParameter("id", userId)
                .getSingleResult();
    }
}
