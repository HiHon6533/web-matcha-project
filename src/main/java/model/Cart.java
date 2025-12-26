package model;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;


@Entity
@Table(name = "carts")
public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartID;
    
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Customer customer;
    
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<ProductLine> products;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getCartID() {
        return cartID;
    }

    public void setCartID(Long cartID) {
        this.cartID = cartID;
    }

    public List<ProductLine> getProducts() {
        return products;
    }

    public void setProducts(List<ProductLine> products) {
        this.products = products;
    }
    
    
}
