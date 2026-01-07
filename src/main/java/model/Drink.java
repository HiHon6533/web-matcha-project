package model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "drinks")
public class Drink extends Product{
    
    @ManyToOne
    @JoinColumn(name = "matcha_id")
    private Matcha matcha;

    @ManyToOne
    @JoinColumn(name = "milk_id")
    private Milk milk;
    
    private String size;
    private BigDecimal price;

    public Matcha getMatcha() {
        return matcha;
    }

    public void setMatcha(Matcha matcha) {
        this.matcha = matcha;
    }

    public Milk getMilk() {
        return milk;
    }

    public void setMilk(Milk milk) {
        this.milk = milk;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal useMilk(){
        if (this.getSize() == null) return BigDecimal.valueOf(0);
        switch (this.getSize().toUpperCase()) {
            case "S": 
                return BigDecimal.valueOf(100); // 100ml
            case "M": 
                return BigDecimal.valueOf(200); // 200ml
            case "XL": 
                return BigDecimal.valueOf(400); // 400ml
            default: 
                return BigDecimal.valueOf(0);
        }
    }
    public BigDecimal useMatcha(){
        if (this.getSize() == null) return BigDecimal.valueOf(0);
        switch (this.getSize().toUpperCase()) {
            case "S": 
                return BigDecimal.valueOf(4); // 4g
            case "M": 
                return BigDecimal.valueOf(6); // 6g
            case "XL": 
                return BigDecimal.valueOf(8); // 8g
            default: 
                return BigDecimal.valueOf(0);
        }
    }
}
