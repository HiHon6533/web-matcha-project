package model;

import jakarta.persistence.*;

@Entity
@Table(name = "milk")
@PrimaryKeyJoinColumn(name = "product_id") // Quan trọng: Để liên kết khóa chính với bảng cha Ingredient
public class Milk extends Ingredient {

    @Column(name = "fatlevel") // Ánh xạ chính xác vào cột fatlevel trong DB
    private int fatLevel;

    @Column(name = "sweetness")
    private String sweetness;

    @Column(name = "flavor")
    private String flavor;

    @Column(name = "texture")
    private int texture;

    // --- GETTER & SETTER GỐC (GIỮ NGUYÊN) ---

    public int getFatLevel() {
        return fatLevel;
    }

    public void setFatLevel(int fatLevel) {
        this.fatLevel = fatLevel;
    }

    public String getSweetness() {
        return sweetness;
    }

    public void setSweetness(String sweetness) {
        this.sweetness = sweetness;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public int getTexture() {
        return texture;
    }

    public void setTexture(int texture) {
        this.texture = texture;
    }

    // --- PHẦN MỚI THÊM: LOGIC CHO GIAO DIỆN (HELPER METHODS) ---

    /**
     * Hàm này được gọi trong JSP bằng cách dùng: ${milk.sweetnessLevel}
     * Nhiệm vụ: Chuyển đổi chữ (String) sang số (int) để hiển thị thanh Bar.
     */
    public int getSweetnessLevel() {
        if (sweetness == null) return 3; // Mặc định trung bình nếu dữ liệu null

        String s = sweetness.toLowerCase().trim();

        if (s.contains("không")) return 0;       // Không đường
        if (s.contains("ít")) return 2;          // Ít ngọt
        if (s.contains("vừa")) return 3;         // Vừa
        if (s.contains("rất ngọt")) return 5;    // Rất ngọt
        if (s.contains("ngọt")) return 4;        // Ngọt

        return 3; // Giá trị mặc định nếu không khớp từ khóa nào
    }
}