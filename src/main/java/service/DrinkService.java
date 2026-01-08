package service;

import dao.ProductDAO;
import dao.MatchaDAO; // Import mới
import dao.MilkDAO;   // Import mới
import model.Drink;
import model.Matcha;
import model.Milk;
import java.util.List;
import java.util.Arrays;

public class DrinkService {

    // Khai báo 3 DAO riêng biệt
    private final ProductDAO productDAO = new ProductDAO();
    private final MatchaDAO matchaDAO = new MatchaDAO();
    private final MilkDAO milkDAO = new MilkDAO();

    // 1. Lấy sản phẩm nổi bật (Dùng ProductDAO)
    public List<Drink> getFeaturedDrinks() {
        // Danh sách ID bạn muốn hiển thị ở trang chủ
        List<Long> ids = Arrays.asList(26L, 27L, 31L); 
        return productDAO.getDrinksByIds(ids);
    }

    // 2. Lấy danh sách Matcha (Dùng MatchaDAO)
    public List<Matcha> getAllMatchaTypes() {
        return matchaDAO.getAllMatcha();
    }

    // 3. Lấy danh sách Sữa (Dùng MilkDAO)
    public List<Milk> getAllMilkTypes() {
        return milkDAO.getAllMilk();
    }
}