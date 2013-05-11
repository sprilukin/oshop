package oshop.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity<Integer> {

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    private ShippingAddress shippingAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    private Discount discount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Prepayment prepayment;

    @OneToMany(fetch = FetchType.LAZY)
    private List<OrderHasStates> states;

    @OneToMany(fetch = FetchType.LAZY)
    private List<OrderHasProducts> products;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public Prepayment getPrepayment() {
        return prepayment;
    }

    public void setPrepayment(Prepayment prepayment) {
        this.prepayment = prepayment;
    }

    public List<OrderHasStates> getStates() {
        return states;
    }

    public void setStates(List<OrderHasStates> states) {
        this.states = states;
    }

    public List<OrderHasProducts> getProducts() {
        return products;
    }

    public void setProducts(List<OrderHasProducts> products) {
        this.products = products;
    }
}
