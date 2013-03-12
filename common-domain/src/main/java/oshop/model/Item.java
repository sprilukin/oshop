package oshop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "item")
public class Item extends BaseEntity<Integer> {

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "categoryId", referencedColumnName = "person_id")
    private ItemCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    private Album album;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}
