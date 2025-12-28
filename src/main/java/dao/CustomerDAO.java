package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import util.JPAUtil;
import model.Account;
import model.Customer;

public class CustomerDAO extends GenericDAO<Customer, Long> {

    public CustomerDAO() {
        super(Customer.class);
    }

    public void findByEmail(String email) {

    }
}
