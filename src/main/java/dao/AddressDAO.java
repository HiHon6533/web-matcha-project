package dao;

import model.Address;
import util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import jakarta.persistence.TypedQuery;


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

    // --- MỚI THÊM: HÀM KIỂM TRA XEM USER ĐÃ CÓ ĐỊA CHỈ NÀO CHƯA ---
    public boolean checkHasAddress(Long userId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // Đếm xem trong DB có bao nhiêu địa chỉ thuộc về userId này
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(a) FROM Address a WHERE a.customer.userID = :uid", Long.class);
            query.setParameter("uid", userId);
            Long count = query.getSingleResult();
            
            return count != null && count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
    // Thêm vào AddressDAO.java
    public java.util.List<Address> getAllAddressByUserId(Long userId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT a FROM Address a WHERE a.customer.userID = :uid ORDER BY a.is_default DESC"; // Mặc định lên đầu
            jakarta.persistence.TypedQuery<Address> query = em.createQuery(jpql, Address.class);
            query.setParameter("uid", userId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new java.util.ArrayList<>();
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