package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.math.BigDecimal;

import model.*;
import java.util.List;
import util.JPAUtil;

public class OrderDAO {

    //Lấy danh sách Order của User
    public List<Order> findByUserId(Long userId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                "SELECT o FROM Order o WHERE o.customer.userID = :id",
                Order.class)
                .setParameter("id", userId)
                .getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    //Tạo order mới từ cart
    public boolean checkout(Customer cus, Cart cart, BigDecimal total){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            //Lấy các productline trong giỏ hàng
            List<ProductLine> lines = cart.getProducts();
            //1. Tạo order mới
            Order newOrder = new Order();
            //Set order của user hiện tại
            newOrder.setCustomer(cus);
            newOrder.setCreatedAt(LocalDateTime.now());
            newOrder.setNote("test");
            newOrder.setOrderStatus("Đã thanh toán!");
            newOrder.setTotal(total);
            //Lưu Order trước để có ID
            em.persist(newOrder);
            //2. Chuyển từ cart sang order và trừ trong kho
            for(ProductLine line: lines){
                //Trừ số lượng trong kho
                Product product = line.getProduct();
                BigDecimal buyQuantity = BigDecimal.valueOf(line.getQuantity());//Số lượng khách mua
                //Product là nguyên liệu
                if (product instanceof Ingredient) {
                    Ingredient ing = (Ingredient) product;
                    // Kiểm tra tồn kho
                    if (ing.getQuantity().compareTo(buyQuantity) < 0) {
                         throw new RuntimeException("Nguyên liệu " + ing.getProductName() + " không đủ hàng bán!");
                    }

                    // Trừ kho trực tiếp
                    ing.setQuantity(ing.getQuantity().subtract(buyQuantity));
                    em.merge(ing);
                }
                else if (product instanceof Drink) {
                    Drink drink = (Drink) product;            
                    BigDecimal milkNeed = drink.useMilk().multiply(buyQuantity);
                    BigDecimal matchaNeed = drink.useMatcha().multiply(buyQuantity);
                    Matcha matcha = drink.getMatcha();
                    Milk milk = drink.getMilk();
                    BigDecimal milkInStock = milk.getQuantity();
                    BigDecimal matchaInStock = matcha.getQuantity();
                    //Kiểm tra tồn kho
                    //need > inStock -> Lỗi
                    if (milkInStock.compareTo(milkNeed) < 0 || matchaInStock.compareTo(matchaNeed) < 0) {
                         throw new RuntimeException("Nguyên liệu không đủ hàng bán!");
                    }
                    matcha.setQuantity(matchaInStock.subtract(matchaNeed));
                    milk.setQuantity(milkInStock.subtract(milkNeed));
                    em.merge(matcha); //Lưu xuống DB
                    em.merge(milk); //Lưu xuống DB
                }
                //Xóa khỏi cart
                line.setCart(null);
                //Thêm vào order
                line.setOrder(newOrder);
                
                em.merge(line); //cập nhật xuống DB
            }
            em.merge(newOrder); // cập nhật xuống DB
            //3. Tạo payment
            Payment payment = new Payment();
            payment.setMethod("VNPay");
            payment.setStatus("Đã thanh toán");
            payment.setCreatedAt(LocalDateTime.now());
            payment.setOrder(newOrder);
            em.persist(payment); // Lưu Payment
            trans.commit();
            return true;
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
    
    public boolean checkoutWithTxn(Customer cus, Cart cart, BigDecimal total, String note, String addressIdStr, String txnRef){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            List<ProductLine> lines = cart.getProducts();
            Order newOrder = new Order();
            newOrder.setCustomer(cus);
            newOrder.setCreatedAt(LocalDateTime.now());
            newOrder.setNote(note == null ? "" : note);
            newOrder.setOrderStatus("Chờ thanh toán"); // chưa thanh toán
            newOrder.setTotal(total);
            // nếu bạn muốn tạo mã order (nếu cần), set ở đây
            em.persist(newOrder);

            // chuyển sản phẩm từ cart -> order và trừ kho (giống checkout cũ)
            for(ProductLine line: lines){
                Product product = line.getProduct();
                BigDecimal buyQuantity = BigDecimal.valueOf(line.getQuantity());
                if (product instanceof Ingredient) {
                    Ingredient ing = (Ingredient) product;
                    if (ing.getQuantity().compareTo(buyQuantity) < 0) {
                         throw new RuntimeException("Nguyên liệu " + ing.getProductName() + " không đủ hàng bán!");
                    }
                    ing.setQuantity(ing.getQuantity().subtract(buyQuantity));
                    em.merge(ing);
                }
                else if (product instanceof Drink) {
                    Drink drink = (Drink) product;            
                    BigDecimal milkNeed = drink.useMilk().multiply(buyQuantity);
                    BigDecimal matchaNeed = drink.useMatcha().multiply(buyQuantity);
                    Matcha matcha = drink.getMatcha();
                    Milk milk = drink.getMilk();
                    BigDecimal milkInStock = milk.getQuantity();
                    BigDecimal matchaInStock = matcha.getQuantity();
                    if (milkInStock.compareTo(milkNeed) < 0 || matchaInStock.compareTo(matchaNeed) < 0) {
                         throw new RuntimeException("Nguyên liệu không đủ hàng bán!");
                    }
                    matcha.setQuantity(matchaInStock.subtract(matchaNeed));
                    milk.setQuantity(milkInStock.subtract(milkNeed));
                    em.merge(matcha);
                    em.merge(milk);
                }
                line.setCart(null);
                line.setOrder(newOrder);
                em.merge(line);
            }
            em.merge(newOrder);

            // Tạo payment, gán transactionId = txnRef, status = "Chờ thanh toán"
            Payment payment = new Payment();
            payment.setMethod("VNPay");
            payment.setStatus("Chờ thanh toán"); // pending
            payment.setCreatedAt(LocalDateTime.now());
            payment.setTransactionId(txnRef); // *** CẦN field transactionId trong Payment entity
            payment.setOrder(newOrder);
            em.persist(payment);

            trans.commit();
            return true;
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    //Lấy tất cả các Order
    public List<Order> getAllOrders(){
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT o FROM Order o ORDER BY o.createdAt DESC", Order.class)
                    .getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    
    
    //Cập nhật trạng thái đơn hàng
    public boolean updateStatus(Long orderId, String newStatus){
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Order order = (Order) em.find(Order.class, orderId);
            if (order != null) {
                order.setOrderStatus(newStatus);
                // Lưu thay đổi xuống DB
                em.getTransaction().commit();
                return true;
            }
            else {
                return false;
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
    
}
