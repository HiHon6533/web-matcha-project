package model;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "products")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productID;

    // Mapping với cột "productname" trong database
    @Column(name = "productname") 
    private String productName;
    
    // --- PHẦN MỚI THÊM VÀO ---
    // Mapping với cột "image" bạn vừa tạo trong database
    @Column(name = "image")
    private String image;
    // -------------------------

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    // --- GETTER & SETTER CHO IMAGE ---
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}