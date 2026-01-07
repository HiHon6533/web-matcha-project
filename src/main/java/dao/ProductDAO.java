package dao;

import model.Product;
import model.Drink;
import model.Ingredient;
import java.util.List;
import java.util.Collections; // Thêm thư viện này để xử lý list rỗng

public class ProductDAO extends GenericDAO<Product, Long> {

    public ProductDAO() {
        super(Product.class);
    }

    public List<Drink> getAllDrinks() {
        // Nên thêm try-catch để tránh lỗi nếu DB chưa ổn định
        try {
            return em.createQuery("FROM Drink", Drink.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Ingredient> getAllIngredients() {
        try {
            return em.createQuery("FROM Ingredient", Ingredient.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    
    // --- HÀM BẠN DÙNG ĐỂ LẤY SẢN PHẨM NỔI BẬT ---
    public List<Drink> getDrinksByIds(List<Long> listId) {
        // Kiểm tra nếu listId bị null hoặc rỗng thì trả về list rỗng ngay, tránh lỗi SQL
        if (listId == null || listId.isEmpty()) {
            return Collections.emptyList();
        }

        try {
            // Logic giữ nguyên: Lấy Drink theo danh sách ID
            return em.createQuery("SELECT d FROM Drink d WHERE d.productID IN :ids", Drink.class)
                     .setParameter("ids", listId)
                     .getResultList();
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console để dễ debug
            return Collections.emptyList();
        }
    }
}