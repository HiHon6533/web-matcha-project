package service;

import java.math.BigDecimal;
import java.util.List;

import dao.CartDAO;
import dao.OrderDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.Customer;
import model.Drink;
import model.Ingredient;
import model.Order;
import model.Product;
import model.ProductLine;

public class OrderService {
    private OrderDAO orderDAO = new OrderDAO();
    private CartDAO cartDAO = new CartDAO();
    
    public List<Order> getAllOrdersByUserId(Long userId){
        return orderDAO.findByUserId(userId);
    }
    
    public boolean createOrder(Customer cus, BigDecimal total){
        Cart cart = cartDAO.findCartByUserId(cus.getUserID());
        if (cart != null){
            orderDAO.checkout(cus, cart, total);
            return true;
        }
        return false;
    }
    
    public boolean createOrderWithTransaction(HttpServletRequest request, BigDecimal total, String note, String addressIdStr, String txnRef) {
        HttpSession session = request.getSession(false);
        Customer cus = (Customer) session.getAttribute("CURRENT_USER");
        Cart cart = cartDAO.findCartByUserId(cus.getUserID());
        if (cart == null) return false;

        // Kiểm tra tồn kho trước khi tạo order/payment
        if (!isStockAvailableForCart(cart)) {
            return false; // gọi từ servlet sẽ set message
        }

        return orderDAO.checkoutWithTxn(cus, cart, total, note, txnRef);
    }

    public boolean isStockAvailableForCart(Cart cart) {
        List<ProductLine> lines = cart.getProducts();
        if (lines == null) return false;
        for (ProductLine line : lines) {
            Product product = line.getProduct();
            BigDecimal qty = BigDecimal.valueOf(line.getQuantity());
            if (product instanceof Ingredient) {
                if (((Ingredient) product).getQuantity().compareTo(qty) < 0) return false;
            } else if (product instanceof Drink) {
                Drink d = (Drink) product;
                BigDecimal milkNeed = d.useMilk().multiply(qty);
                BigDecimal matchaNeed = d.useMatcha().multiply(qty);
                if (d.getMilk().getQuantity().compareTo(milkNeed) < 0 || d.getMatcha().getQuantity().compareTo(matchaNeed) < 0) {
                    return false;
                }
            }
        }
        return true;
    }

    // Finalize wrapper
    public boolean finalizePaymentAfterVNPay(String txnRef) {
        return orderDAO.finalizeOrderPayment(txnRef);
    }
 
    
}
