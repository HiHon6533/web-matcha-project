package dao;

import model.Cart;

public class AddressDAO extends GenericDAO<Cart, Long> {

    public AddressDAO() {
        super(Cart.class);
    }
}