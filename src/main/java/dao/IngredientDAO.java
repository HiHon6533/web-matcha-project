package dao;

import model.Ingredient;
import util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.math.BigDecimal;
import java.util.List;

public class IngredientDAO {

    /**
     * Cập nhật thông tin Ingredient (Bao gồm cả thông tin từ bảng cha Product)
     * JPA sẽ tự động xử lý việc update vào 2 bảng 'products' và 'ingredients'
     */
    public boolean updateIngredientFull(String idStr, String name, BigDecimal price, BigDecimal quantity, String unit, String image) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();

            // 1. Chuyển đổi ID (Vì trong Servlet là String, nhưng trong DB thường là Long)
            Long id = Long.parseLong(idStr);

            // 2. Tìm thực thể Ingredient hiện có theo ID
            Ingredient ingredient = em.find(Ingredient.class, id);

            if (ingredient != null) {
                // --- Cập nhật thuộc tính của cha (Product) ---
                ingredient.setProductName(name); // Hoặc setName(name) tùy vào model Product của bạn
                ingredient.setImage(image);

                // --- Cập nhật thuộc tính của con (Ingredient) ---
                ingredient.setPricePerUnit(price);
                ingredient.setQuantity(quantity);
                ingredient.setUnit(unit);

                // 3. Lưu thay đổi (Merge)
                em.merge(ingredient);
                
                trans.commit();
                return true;
            } else {
                // Không tìm thấy ID tương ứng
                return false;
            }

        } catch (Exception e) {
            if (trans.isActive()) {
                trans.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    // Các hàm khác (Lấy danh sách, xóa, v.v...) viết tương tự style AccountDAO
    
    public List<Ingredient> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT i FROM Ingredient i", Ingredient.class).getResultList();
        } finally {
            em.close();
        }
    }
}