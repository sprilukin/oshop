package oshop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "orderState")
public class OrderState extends BaseEntity<Integer> {

    @Column(name = "name")
    private String name;

    @Column(name = "label")
    private String label;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
