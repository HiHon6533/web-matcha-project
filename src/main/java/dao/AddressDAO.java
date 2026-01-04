package dao;

import model.Address;
import util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class AddressDAO
{
    public boolean removeAllDefault(Long userId) 
    {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try 
        {
            trans.begin();
            em.createQuery("UPDATE Address a SET a.is_default = false WHERE a.customer.userID = :uid")
              .setParameter("uid", userId)
              .executeUpdate();
            trans.commit();
            return true;
        } 
        catch (Exception e) 
        {
            if (trans.isActive()) trans.rollback();
            e.printStackTrace();
            return false;
        } 
        finally 
        {
            em.close();
        }
    }

    public boolean setAsDefault(Long addressId) 
    {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try 
        {
            trans.begin();
            Address address = em.find(Address.class, addressId);
            if (address != null) 
            {
                address.setIs_default(true);
                em.merge(address);
            }
            trans.commit();
            return true;
        } 
        catch (Exception e) 
        {
            if (trans.isActive()) trans.rollback();
            e.printStackTrace();
            return false;
        } 
        finally {
            em.close();
        }
    }
    
    public boolean insertAddress(Address address)
    {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try
        {
            trans.begin();
            em.persist(address);
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
}