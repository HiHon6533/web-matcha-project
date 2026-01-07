package model;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "addresses")
public class Address implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressID;
    private Boolean is_default;
    private String province;
    private String ward;
    private String hamlet;
    private String house_number;
    private String note;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getAddressID() {
        return addressID;
    }

    public void setAddressID(Long addressID) {
        this.addressID = addressID;
    }

    public Boolean getIs_default() {
        return is_default;
    }

    public void setIs_default(Boolean is_default) {
        this.is_default = is_default;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getHamlet() {
        return hamlet;
    }

    public void setHamlet(String hamlet) {
        this.hamlet = hamlet;
    }

    public String getHouse_number() {
        return house_number;
    }

    public void setHouse_number(String house_number) {
        this.house_number = house_number;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
    @Transient
    public String getAddressDetail() {
        return house_number + ", " + hamlet + ", " + ward + ", " + province;
    }
    
    @Transient
    public String getReceiverName() {
        return customer != null ? customer.getFullName() : "";
    }

    @Transient
    public String getPhoneNumber() {
        return customer != null ? customer.getPhoneNumber() : "";
    }
}
