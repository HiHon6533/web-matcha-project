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

    
    
}
