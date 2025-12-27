package dao;

import model.Address;

public class AddressDAO extends GenericDAO<Address, Long> {

    public AddressDAO() {
        super(Address.class);
    }
}