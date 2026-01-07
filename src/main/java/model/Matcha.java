package model;

import java.io.Serializable;
import jakarta.persistence.*;


@Entity
@Table(name = "matcha") 
@PrimaryKeyJoinColumn(name = "product_id")
public class Matcha extends Ingredient {

    // 1. Khai báo đúng với Database
    @Column(name = "strength")
    private int strength;      // Đã là số

    @Column(name = "bitterness")
    private int bitterness;    // Đã là số

    @Column(name = "color")
    private String color;      // Đang là chữ (VD: "Xanh đậm")

    @Column(name = "aroma")
    private String aroma;      // Đang là chữ

    @Column(name = "caffeine")
    private String caffeine;   // Đang là chữ

    // --- GETTER & SETTER CHUẨN ---
    public int getStrength() { return strength; }
    public void setStrength(int strength) { this.strength = strength; }

    public int getBitterness() { return bitterness; }
    public void setBitterness(int bitterness) { this.bitterness = bitterness; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    // ... (Tự thêm Getter/Setter cho aroma và caffeine tương tự) ...

    // --- 2. HÀM PHỤ TRỢ: CHUYỂN ĐỔI CHỮ SANG SỐ CHO THANH HIỂN THỊ ---
    
    // Logic: Nếu database là "Xanh đậm" -> trả về 5, "Xanh nhạt" -> trả về 2...
    public int getColorLevel() {
        if (color == null) return 5;
        String c = color.toLowerCase();
        if (c.contains("đậm")) return 10;
        if (c.contains("tươi")) return 8;
        if (c.contains("sáng")) return 6;
        return 2; // Mặc định
    }

    public int getAromaLevel() {
        // Tạm trả về 4 hoặc viết logic tương tự trên
        return 5; 
    }

    public int getCaffeineLevel() {
        if (caffeine == null) return 5;
        String c = caffeine.toLowerCase();
        if (c.contains("rất thấp")) return 2;
        if (c.contains("thấp")) return 4;
        if (c.contains("cao")) return 9;
        return 3; // Trung bình
    }
}