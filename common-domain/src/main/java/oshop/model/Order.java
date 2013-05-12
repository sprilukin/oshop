package oshop.model;

import org.hibernate.annotations.Formula;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity<Integer> {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_address_id")
    private ShippingAddress shippingAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prepayment_id")
    private Prepayment prepayment;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="order", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<OrderHasOrderStates> states = new ArrayList<OrderHasOrderStates>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinTable(
        name = "order_products",
        joinColumns = {@JoinColumn(name = "order_id", nullable = false, updatable = false)},
        inverseJoinColumns = { @JoinColumn(name = "product_id", nullable = false, updatable = false) }
    )
    private List<Product> products = new ArrayList<Product>();

    @Formula("( SELECT s.name FROM order_has_order_states h INNER JOIN order_state s ON h.order_state_id = s.id " +
            "WHERE h.order_id = id ORDER BY h.date DESC, h.id DESC LIMIT 1 )")
    private String currentOrderStateName;

    @Formula("( SELECT count(p.product_id) FROM order_products p WHERE p.order_id = id )")
    private Integer productsCount;

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

    public List<OrderHasOrderStates> getStates() {
        return states;
    }

    public void setStates(List<OrderHasOrderStates> states) {
        this.states = states;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Integer getProductsCount() {
        return productsCount;
    }

    public void setProductsCount(Integer productsCount) {
        this.productsCount = productsCount;
    }

    public String getCurrentOrderStateName() {
        return currentOrderStateName;
    }

    public void setCurrentOrderStateName(String currentOrderStateName) {
        this.currentOrderStateName = currentOrderStateName;
    }
}
