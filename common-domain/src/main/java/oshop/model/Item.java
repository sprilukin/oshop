package oshop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Entity
@Table(name = "item")
@XmlRootElement
public class Item {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;

    private BigDecimal price;

    @ManyToOne(targetEntity = ItemCategory.class)
    private ItemCategory category;
}
