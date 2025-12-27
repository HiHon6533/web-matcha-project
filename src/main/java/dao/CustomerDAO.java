package dao;

import model.Customer;

public class CustomerDAO extends GenericDAO<Customer, Long> {

    public CustomerDAO() {
        super(Customer.class);
    }

    public Customer findByEmail(String email) {
        return em.createQuery(
                "SELECT c FROM Customer c WHERE c.account.email = :email",
                Customer.class)
                .setParameter("email", email)
                .getSingleResult();
    }
}
