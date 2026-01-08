package service;

import dao.CartDAO;
import model.*;
import java.util.ArrayList;
import java.util.List;

public class CartService {
    private CartDAO cartDAO = new CartDAO();
    
    // Hàm xử lý logic thêm vào List và cộng dồn số lượng
    public void addProductToCartLogic(Long userId, Product product, int quantity) {
        if (product == null) return;

        // Lấy Cart, nếu chưa có thì tạo mới
        Cart cart = cartDAO.findCartByUserId(userId);
        if (cart == null) {
            Customer cus = cartDAO.findCustomerById(userId);
            cart = cartDAO.createCart(cus);
        }

        // Kiểm tra xem sản phẩm đã có trong ProductLine chưa
        boolean exists = false;
        List<ProductLine> lines = cart.getProducts();        
        lines = (lines == null) ? new ArrayList<>() : lines;

        for (ProductLine line : lines) {
            //Kiểm tra đã có chưa
            if (line.getProduct().getProductID().equals(product.getProductID())) {
                // Đã có -> Cộng dồn số lượng
                line.setQuantity(line.getQuantity() + quantity);
                cartDAO.saveCartItem(line);
                exists = true;
                break;
            }
        }
        // Nếu chưa có -> Tạo productline mới
        if (!exists) {
            ProductLine newLine = new ProductLine();
            newLine.setCart(cart);
            newLine.setProduct(product);
            newLine.setQuantity(quantity);
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
            Customer cus = cartDAO.findCustomerById(userId);
            cart = cartDAO.createCart(cus);
        }
        return cart;
    }
    
    //Tăng số lượng
    public void increaseQuantity(Long lineId){
        ProductLine line = cartDAO.findLineById(lineId);
        if (line.getProduct().getProductID() <= 5) line.setQuantity(line.getQuantity() + 10); //Matcha
        else if (line.getProduct().getProductID() <= 10) line.setQuantity(line.getQuantity() + 100); //Milk
        else line.setQuantity(line.getQuantity() + 1);//Drink
        cartDAO.saveCartItem(line);
    }
    
    //Giảm số lượng
    public void decreaseQuantity(Long lineId){
        ProductLine line = cartDAO.findLineById(lineId);
        int quantity = 1;//Drink
        if (line.getProduct().getProductID() <= 5) quantity = 10; //Matcha
        else if (line.getProduct().getProductID() <= 10) quantity = 100; //Milk
        if (line.getQuantity() <= quantity) cartDAO.deleteLine(lineId);
        else {
            line.setQuantity(line.getQuantity() - quantity ); 
            cartDAO.saveCartItem(line);
        }
    }
    //Xóa sản phẩm khỏi giỏ hàng
    public void removeLine(Long lineId){
        cartDAO.deleteLine(lineId);
    }
    
    public Cart findCartByUserId(Long userId) {
        return cartDAO.findCartByUserId(userId);
    }
    
}
