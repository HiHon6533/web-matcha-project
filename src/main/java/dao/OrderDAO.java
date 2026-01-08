package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
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
    
    public boolean checkoutWithTxn(Customer cus, Cart cart, BigDecimal total, String note, String txnRef){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();

            Order newOrder = new Order();
            newOrder.setCustomer(cus);
            newOrder.setCreatedAt(LocalDateTime.now());
            newOrder.setNote(note == null ? "" : note);
            newOrder.setOrderStatus("Chờ thanh toán"); // chưa thanh toán
            newOrder.setTotal(total);
            em.persist(newOrder);

            // Tạo payment, gán transactionId = txnRef, status = "Chờ thanh toán"
            Payment payment = new Payment();
            payment.setMethod("VNPay");
            payment.setStatus("Chờ thanh toán");
            payment.setCreatedAt(LocalDateTime.now());
            payment.setTransactionId(txnRef);
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

    public boolean finalizeOrderPayment(String txnRef) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            List<Payment> payments = em.createQuery(
                "SELECT p FROM Payment p WHERE p.transactionId = :txRef", Payment.class)
                .setParameter("txRef", txnRef)
                .getResultList();

            if (payments.isEmpty()) {
                throw new RuntimeException("Payment với txnRef không tồn tại: " + txnRef);
            }

            Payment p = payments.get(0);
            if (!"Chờ thanh toán".equals(p.getStatus())) {
                // đã được finalize trước đó hoặc trạng thái khác
                tx.commit();
                return false;
            }

            Order order = em.find(Order.class, p.getOrder().getOrderID());
            Customer cus = order.getCustomer();

            // Lấy cart hiện tại của user
            Cart cart = em.createQuery("SELECT c FROM Cart c WHERE c.customer.userID = :uid", Cart.class)
                          .setParameter("uid", cus.getUserID())
                          .getSingleResult();

            List<ProductLine> lines = cart.getProducts();
            if (lines == null || lines.isEmpty()) {
                throw new RuntimeException("Cart trống khi finalize payment.");
            }

            // 1) Kiểm tra tồn kho với lock pessimistic cho mỗi Product/Ingredient liên quan
            for (ProductLine line : lines) {
                Product product = em.find(Product.class, line.getProduct().getProductID());
                // giữ lock để tránh người khác trừ cùng lúc
                em.lock(product, LockModeType.PESSIMISTIC_WRITE);

                BigDecimal buyQuantity = BigDecimal.valueOf(line.getQuantity());
                if (product instanceof Ingredient) {
                    Ingredient ing = (Ingredient) product;
                    if (ing.getQuantity().compareTo(buyQuantity) < 0) {
                        throw new RuntimeException("Nguyên liệu " + ing.getProductName() + " không đủ hàng bán!");
                    }
                } else if (product instanceof Drink) {
                    Drink drink = (Drink) product;
                    Matcha matcha = drink.getMatcha();
                    Milk milk = drink.getMilk();
                    // lock components
                    Matcha matchaLocked = em.find(Matcha.class, matcha.getProductID());
                    em.lock(matchaLocked, LockModeType.PESSIMISTIC_WRITE);
                    Milk milkLocked = em.find(Milk.class, milk.getProductID());
                    em.lock(milkLocked, LockModeType.PESSIMISTIC_WRITE);

                    BigDecimal milkNeed = drink.useMilk().multiply(buyQuantity);
                    BigDecimal matchaNeed = drink.useMatcha().multiply(buyQuantity);
                    if (milkLocked.getQuantity().compareTo(milkNeed) < 0 || matchaLocked.getQuantity().compareTo(matchaNeed) < 0) {
                        throw new RuntimeException("Nguyên liệu không đủ cho đồ uống " + drink.getProductName());
                    }
                }
            }

            // 2) Nếu kiểm tra thành công, thực hiện trừ kho & move lines từ cart -> order
            for (ProductLine line : lines) {
                Product product = em.find(Product.class, line.getProduct().getProductID());

                BigDecimal buyQuantity = BigDecimal.valueOf(line.getQuantity());
                if (product instanceof Ingredient) {
                    Ingredient ing = (Ingredient) product;
                    ing.setQuantity(ing.getQuantity().subtract(buyQuantity));
                    em.merge(ing);
                } else if (product instanceof Drink) {
                    Drink drink = (Drink) product;
                    Matcha matchaLocked = em.find(Matcha.class, drink.getMatcha().getProductID());
                    Milk milkLocked = em.find(Milk.class, drink.getMilk().getProductID());

                    BigDecimal milkNeed = drink.useMilk().multiply(buyQuantity);
                    BigDecimal matchaNeed = drink.useMatcha().multiply(buyQuantity);

                    matchaLocked.setQuantity(matchaLocked.getQuantity().subtract(matchaNeed));
                    milkLocked.setQuantity(milkLocked.getQuantity().subtract(milkNeed));
                    em.merge(matchaLocked);
                    em.merge(milkLocked);
                }

                // move line
                line.setCart(null);
                line.setOrder(order);
                em.merge(line);
            }

            order.setOrderStatus("Đã thanh toán");
            p.setStatus("Đã thanh toán");
            em.merge(order);
            em.merge(p);

            tx.commit();
            return true;
        } catch (Exception ex) {
            if (tx.isActive()) tx.rollback();
            ex.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
 
    public boolean markPaymentFailed(String txnRef, String reason) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            List<Payment> payments = em.createQuery(
                "SELECT p FROM Payment p WHERE p.transactionId = :txRef", Payment.class)
                .setParameter("txRef", txnRef)
                .getResultList();
            if (payments.isEmpty()) {
                tx.commit();
                return false;
            }
            Payment p = payments.get(0);
            p.setStatus("Thanh toán thất bại");
            // Optionally set a failure reason field if you have one
            em.merge(p);
            Order o = em.find(Order.class, p.getOrder().getOrderID());
            if (o != null) {
                o.setOrderStatus("Thanh toán thất bại");
                em.merge(o);
            }
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

}
