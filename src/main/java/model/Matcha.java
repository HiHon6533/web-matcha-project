package model;

import jakarta.persistence.*;

@Entity
@Table(name = "matcha")
public class Matcha extends Ingredient{
    private int strength;
    private int bitterness;
    private String color;
    private String aroma;
    private String caffeine;

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getBitterness() {
        return bitterness;
    }

    public void setBitterness(int bitterness) {
        this.bitterness = bitterness;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAroma() {
        return aroma;
    }

    public void setAroma(String aroma) {
        this.aroma = aroma;
    }

    public String getCaffeine() {
        return caffeine;
    }

    public void setCaffeine(String caffeine) {
        this.caffeine = caffeine;
    }
    
    
}
