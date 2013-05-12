package oshop.model;

import org.hibernate.annotations.Formula;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy="order", orphanRemoval = true)
    private Set<OrderHasOrderStates> states;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "order_products",
        joinColumns = {@JoinColumn(name = "order_id", nullable = false, updatable = false)},
        inverseJoinColumns = { @JoinColumn(name = "product_id", nullable = false, updatable = false) }
    )
    private Set<Product> products;

    @Formula("(select count(p.id) from orders_order_has_products p where p.orders_id = id)")
    private Integer productsCount;

    //@Formula("(select st.name from (select s.name, order_has_states h inner join order_state s on h.states_id = s.id where h.orders_id = id) st)")
    //private String currentOrderState;

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

    public Set<OrderHasOrderStates> getStates() {
        return states;
    }

    public void setStates(Set<OrderHasOrderStates> states) {
        this.states = states;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Integer getProductsCount() {
        return productsCount;
    }
}
