package oshop.model;

import org.hibernate.annotations.Formula;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity<Integer> {

    @Size(max = 255)
    @Column(name = "description")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_date")
    private Date date = new Date();

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
    @JoinColumn(name = "additional_payment_id")
    private AdditionalPayment additionalPayment;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="order", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<OrderHasOrderStates> states = new ArrayList<OrderHasOrderStates>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinTable(
        name = "order_products",
        joinColumns = {@JoinColumn(name = "order_id", nullable = false, updatable = false)},
        inverseJoinColumns = { @JoinColumn(name = "product_id", nullable = false, updatable = false) },
        uniqueConstraints = {@UniqueConstraint(name = "UK_order_product", columnNames = {"order_id", "product_id"})}
    )
    private List<Product> products = new ArrayList<Product>();

    @Formula(OrderCalcFieldQueries.CURRENT_ORDER_STATE_NAME_SQL)
    private String currentOrderStateName;

    @Formula(OrderCalcFieldQueries.CURRENT_ORDER_STATE_DATE_SQL)
    private Date currentOrderStateDate;

    @Formula(OrderCalcFieldQueries.PRODUCTS_COUNT_SQL)
    private Integer productsCount;

    @Formula(OrderCalcFieldQueries.PRODUCTS_PRICE_SQL)
    private BigDecimal productsPrice;

    @Formula(OrderCalcFieldQueries.TOTAL_PRICE_SQL)
    private BigDecimal totalPrice;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

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

    public AdditionalPayment getAdditionalPayment() {
        return additionalPayment;
    }

    public void setAdditionalPayment(AdditionalPayment additionalPayment) {
        this.additionalPayment = additionalPayment;
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

    public Date getCurrentOrderStateDate() {
        return currentOrderStateDate;
    }

    public void setCurrentOrderStateDate(Date currentOrderStateDate) {
        this.currentOrderStateDate = currentOrderStateDate;
    }

    public BigDecimal getProductsPrice() {
        return productsPrice;
    }

    public void setProductsPrice(BigDecimal productsPrice) {
        this.productsPrice = productsPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }
}
