package dao;

import model.Address;
import util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class AddressDAO {

    public boolean changeDefaultAddress(Long userId, Long newDefaultAddressId) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
           
            em.createQuery("UPDATE Address a SET a.is_default = false WHERE a.customer.userID = :uid")
              .setParameter("uid", userId)
              .executeUpdate();

            Address address = em.find(Address.class, newDefaultAddressId);
            if (address != null) {
                address.setIs_default(true);
            }
            
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

    public boolean insertAddress(Address address) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();

            if (address.getIs_default() == true) {
                  em.createQuery("UPDATE Address a SET a.is_default = false WHERE a.customer.userID = :uid")
                  .setParameter("uid", address.getCustomer().getUserID())
                  .executeUpdate();
            }

            em.persist(address);
            
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
    
    public boolean deleteAddress(Long addressId, Long userId) {
    EntityManager em = JPAUtil.getEntityManager();
    EntityTransaction trans = em.getTransaction();
    try {
        trans.begin();
        int deletedCount = em.createQuery("DELETE FROM Address a WHERE a.addressID = :aid AND a.customer.userID = :uid")
                             .setParameter("aid", addressId)
                             .setParameter("uid", userId)
                             .executeUpdate();
        trans.commit();
        
        return deletedCount > 0;
    } catch (Exception e) {
        if (trans.isActive()) trans.rollback();
        e.printStackTrace();
        return false;
    } finally {
        em.close();
    }
}
    public List<Address> findByUserId(Long userId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                "SELECT a FROM Address a WHERE a.customer.userID = :uid",
                Address.class
            )
            .setParameter("uid", userId)
            .getResultList();
        } finally {
            em.close();
        }
    }

}