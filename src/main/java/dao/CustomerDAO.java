package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import util.JPAUtil;
import model.Account;
import model.Customer;

public class CustomerDAO{

    //Tim user bang email
    public Customer findByAccountEmail(String email) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
        return em.createQuery(
            "SELECT c FROM Customer c WHERE c.account.email = :email",
            Customer.class
        )
        .setParameter("email", email)
        .getSingleResult();
    } catch (NoResultException e) {
        return null;
    } finally {
        em.close();
    }
}
}
