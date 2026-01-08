package dao;

import model.Matcha;
import util.JPAUtil; // Import class tiện ích kết nối của bạn
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Collections;

public class MatchaDAO extends GenericDAO<Matcha, Long> {

    public MatchaDAO() {
        super(Matcha.class);
    }

    // Hàm lấy toàn bộ danh sách Matcha
    public List<Matcha> getAllMatcha() {
        // 1. Lấy EntityManager mới cho thao tác này
        EntityManager em = JPAUtil.getEntityManager();
        
        try {
            // 2. QUAN TRỌNG: Xóa bộ nhớ đệm (Cache Level 1)
            // Lệnh này bắt buộc Hibernate quên dữ liệu cũ đi và lấy cái mới từ DB
            em.clear(); 
            
            // 3. Tạo câu truy vấn
            TypedQuery<Matcha> query = em.createQuery("SELECT m FROM Matcha m ORDER BY m.id ASC", Matcha.class);
            
            // (Tùy chọn) Thêm dòng này để ép DB trả dữ liệu mới nhất (Cache Level 2)
            query.setHint("jakarta.persistence.cache.storeMode", "REFRESH");

            return query.getResultList();

        } catch (Exception e) { 
            e.printStackTrace(); // Nên in lỗi để debug nếu có sự cố
            return Collections.emptyList();
        } finally {
            // 4. Luôn đóng EntityManager để trả lại tài nguyên
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}