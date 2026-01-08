package dao;

import model.Milk;
import util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Collections;

public class MilkDAO extends GenericDAO<Milk, Long> {

    public MilkDAO() {
        super(Milk.class);
    }

    // Hàm lấy toàn bộ danh sách Milk
    public List<Milk> getAllMilk() {
        // 1. Mở kết nối mới
        EntityManager em = JPAUtil.getEntityManager();
        
        try {
            // 2. Xóa Cache để tránh hiện dữ liệu cũ sau khi Update
            em.clear(); 
            
            // 3. Truy vấn dữ liệu
            TypedQuery<Milk> query = em.createQuery("SELECT m FROM Milk m", Milk.class);
            
            // (Tùy chọn) Ép buộc refresh từ DB
            query.setHint("jakarta.persistence.cache.storeMode", "REFRESH");

            return query.getResultList();

        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console để dễ debug
            return Collections.emptyList(); // Trả về list rỗng thay vì null để tránh lỗi NullPointer
        } finally {
            // 4. Đóng kết nối
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}