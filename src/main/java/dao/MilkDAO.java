package dao;

import model.Milk;
import java.util.List;
import java.util.Collections;

// Kế thừa GenericDAO với type là Milk
public class MilkDAO extends GenericDAO<Milk, Long> {

    public MilkDAO() {
        super(Milk.class);
    }

    // Hàm lấy toàn bộ danh sách Sữa
    public List<Milk> getAllMilk() {
        try {
            return em.createQuery("FROM Milk", Milk.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}