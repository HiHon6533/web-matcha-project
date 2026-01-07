package dao;

import model.Matcha;
import java.util.List;
import java.util.Collections;

// Kế thừa GenericDAO với type là Matcha
public class MatchaDAO extends GenericDAO<Matcha, Long> {

    public MatchaDAO() {
        super(Matcha.class);
    }

    // Hàm lấy toàn bộ danh sách Matcha
    public List<Matcha> getAllMatcha() {
        try {
            // JPQL cực gọn vì Hibernate tự hiểu Matcha kế thừa Ingredient/Product
            return em.createQuery("FROM Matcha", Matcha.class).getResultList();
        } catch (Exception e) { 
            
            return Collections.emptyList();
        }
    }
}