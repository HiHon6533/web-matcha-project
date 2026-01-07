package model;

import java.math.BigDecimal;
import jakarta.persistence.*; // Nếu dùng Tomcat 10+ thì đổi thành jakarta.persistence.*

@Entity
@Table(name = "ingredients")
@PrimaryKeyJoinColumn(name = "product_id") // Quan trọng: Liên kết ID với bảng cha 'products'
public class Ingredient extends Product {

    @Column(name = "unit")
    private String unit;

    // Map chính xác tên cột trong DB là 'priceperunit' (chữ thường)
    @Column(name = "priceperunit") 
    private BigDecimal pricePerUnit;

    @Column(name = "origin")
    private String origin;

    @Column(name = "quantity")
    private BigDecimal quantity;

    // --- GETTER & SETTER ---

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(BigDecimal pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    // --- HÀM TIỆN ÍCH (ALIAS) ---
    // Giúp gọi ${item.price} trên JSP vẫn chạy được mà không lỗi
    @Transient // Báo cho JPA biết đây không phải là cột trong DB
    public BigDecimal getPrice() {
        return this.pricePerUnit;
    }

    public void setPrice(BigDecimal price) {
        this.pricePerUnit = price;
    }
    // Hàm phụ trợ để tính giá cho 50g
    public java.math.BigDecimal getPrice50g() {
        if (this.pricePerUnit == null) {
            return java.math.BigDecimal.ZERO;
        }
        // Lấy giá gốc nhân với 50
        return this.pricePerUnit.multiply(new java.math.BigDecimal(50));
    }
    public java.math.BigDecimal getPrice100() {
        if (this.pricePerUnit == null) {
            return java.math.BigDecimal.ZERO;
        }
        // Lấy giá gốc nhân với 100
        return this.pricePerUnit.multiply(new java.math.BigDecimal(100));
    }
}
