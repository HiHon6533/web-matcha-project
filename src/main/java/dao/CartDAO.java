package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

import model.*;
import util.JPAUtil;

public class CartDAO {    
    // Lấy giỏ hàng của Customer (nếu chưa có thì trả về null hoặc tạo mới ở Service)
    public Cart findCartByUserId(Long userId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String query = "SELECT c FROM Cart c WHERE c.customer.userID = :uid";
            return em.createQuery(query, Cart.class)
                     .setParameter("uid", userId)
                     .getSingleResult();
        } catch (Exception e) {
            return null; // Chưa có giỏ hàng
        } finally {
            em.close();
        }
    }
    
    // Tìm Product cơ bản theo ID (Dùng cho Standard)
    public Product findProductById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Product.class, id);
        } finally {
            em.close();
        }
    }
    
    // Tìm Drink theo công thức (Matcha ID + Milk ID + Size) (Dùng cho Custom)
    public Drink findDrinkByComponents(Long matchaId, Long milkId, String size) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT d FROM Drink d " +
                          "WHERE d.matcha.productID = :mId " +
                          "AND d.milk.productID = :mkId " +
                          "AND d.size = :s";
            
            return em.createQuery(jpql, Drink.class)
                     .setParameter("mId", matchaId)
                     .setParameter("mkId", milkId)
                     .setParameter("s", size)
                     .getSingleResult(); // Trả về Drink nếu tìm thấy
        } catch (NoResultException e) {
            return null; // Không tìm thấy món nào khớp công thức
        } finally {
            em.close();
        }
    }
    
    // Lưu/Cập nhật dòng sản phẩm vào giỏ
    public void saveCartItem(ProductLine item) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(item); // merge tự động insert nếu mới, update nếu cũ
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
    
    // Tạo mới Cart nếu User chưa có (Optional)
    public Cart createCart(Customer customer) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        Cart cart = new Cart();
        cart.setCustomer(customer);
        try {
            trans.begin();
            em.persist(cart);
            trans.commit();
            return cart;
        } catch (Exception e) {
            trans.rollback();
            return null;
        } finally {
            em.close();
        }
    }
    // Hàm phụ tìm Customer để tạo Cart
    public Customer findCustomerById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try { return em.find(Customer.class, id); } finally { em.close(); }
    }
}
