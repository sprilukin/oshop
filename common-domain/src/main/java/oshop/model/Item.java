package oshop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "item")
public class Item extends BaseEntity<Integer> {

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private ItemCategory category;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        //this.price = price;
        this.price = price != null ? price.setScale(2, RoundingMode.HALF_UP) : null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemCategory getCategory() {
        return category;
    }

    public void setCategory(ItemCategory category) {
        this.category = category;
    }
}
