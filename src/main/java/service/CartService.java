package service;

import dao.CartDAO;
import model.*;
import java.util.ArrayList;
import java.util.List;

public class CartService {
    private CartDAO cartDAO = new CartDAO();
    
    // Hàm xử lý logic thêm vào List và cộng dồn số lượng
    private void addProductToCartLogic(Long userId, Product product, int quantity) {
        if (product == null) return;

        // 1. Lấy Cart, nếu chưa có thì tạo mới
        Cart cart = cartDAO.findCartByUserId(userId);
        if (cart == null) {
            Customer cus = cartDAO.findCustomerById(userId);
            cart = cartDAO.createCart(cus);
        }

        // 2. Kiểm tra xem sản phẩm đã có trong ProductLine chưa
        boolean exists = false;
        List<ProductLine> lines = cart.getProducts();        
        lines = (lines == null) ? new ArrayList<>() : lines;

        for (ProductLine line : lines) {
            // So sánh Product ID
            if (line.getProduct().getProductID().equals(product.getProductID())) {
                // Đã có -> Cộng dồn số lượng
                line.setQuantity(line.getQuantity() + quantity);
                cartDAO.saveCartItem(line);
                exists = true;
                break;
            }
        }
        // 3. Nếu chưa có -> Tạo dòng mới
        if (!exists) {
            ProductLine newLine = new ProductLine();
            newLine.setCart(cart);
            newLine.setProduct(product);
            newLine.setQuantity(quantity);
            // Quan trọng: Order để null vì đây là Cart
            newLine.setOrder(null); 
            
            cartDAO.saveCartItem(newLine);
        }
    }
    // Thêm sản phẩm type= STANDARD
    public boolean addStandardProduct(Long userId, Long productId, int quantity) {
        Product product = cartDAO.findProductById(productId);
        if (product != null) {
            addProductToCartLogic(userId, product, quantity);
            return true;
        }
        return false;
    }

    // Thêm sản phẩm type= CUSTOM DRINK
    public boolean addCustomDrink(Long userId, Long matchaId, Long milkId, String size, int quantity) {
        // Tìm Drink dựa trên component ID
        Drink drink = cartDAO.findDrinkByComponents(matchaId, milkId, size);
        
        if (drink != null) {
            // Nếu tìm thấy Drink (ID có sẵn trong DB), coi nó như Product bình thường
            addProductToCartLogic(userId, drink, quantity);
            return true;
        } else {
            // Không tìm thấy loại nước này trong Menu
            return false;
        }
    }
    
    //Tìm cart bằng userId 
    public Cart getCartByUserID(Long userId){
        Cart cart = cartDAO.findCartByUserId(userId);
        if (cart == null){
            throw new RuntimeException("Không tìm thấy cart tương ứng");
        }
        return cart;
    }
}
