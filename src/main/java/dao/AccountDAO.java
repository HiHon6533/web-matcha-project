package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.EntityTransaction;

import model.Account;
import model.Customer;
import util.JPAUtil;

public class AccountDAO {
    //Lấy account bằng email
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
    //Kiểm tra email có tồn tại chưa
    public boolean checkEmail(String email) 
    {
        EntityManager em = JPAUtil.getEntityManager();
        try
        {
            Long count = em.createQuery("SELECT COUNT(a) FROM Account a WHERE a.email = :email", Long.class)
                           .setParameter("email", email)
                           .getSingleResult();
            return count > 0;
        } 
        finally 
        {
            em.close();
        }
    }

    public boolean register(Account account, Customer customer) 
    {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();

        try 
        {
            
            trans.begin();
            customer.setAccount(account);
            em.persist(account);
            em.persist(customer);
            trans.commit();
            return true;
        } 
        catch (Exception e) 
        {

            if (trans.isActive()) 
            {
                trans.rollback();
            }
            e.printStackTrace();
            return false;
        } 
        finally 
        {
            em.close();
                        
        }
    }

    public Account findByToken(String token) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Account> query = em.createQuery(
                "SELECT a FROM Account a WHERE a.token = :token", 
                Account.class
            );
            query.setParameter("token", token);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public boolean update(Account account) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(account);
            trans.commit();
            return true;
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
}
