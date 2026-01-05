package model;

import jakarta.persistence.*;

@Entity
@Table(name = "milk")
public class Milk extends Ingredient{
    private int fatLevel;
    private String sweetness;
    private String flavor;
    private int texture;

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
    
    
}
