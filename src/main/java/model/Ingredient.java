package model;

import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
@Table(name = "ingredients")
public class Ingredient extends Product{
    private String unit;
    private BigDecimal pricePerUnit;
    private String origin;
    private BigDecimal quantity;
    
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
    
}
