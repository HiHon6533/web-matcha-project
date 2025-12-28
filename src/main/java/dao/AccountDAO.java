package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import model.Account;
import util.JPAUtil;

public class AccountDAO {
    public Account findByEmail(String email) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            TypedQuery<Account> query =
                em.createQuery(
                    "SELECT a FROM Account a WHERE a.email = :email",
                    Account.class
                );

            query.setParameter("email", email);

            return query.getSingleResult();

        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

}
