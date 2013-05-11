package oshop.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "orderState")
public class OrderState extends BaseEntity<Integer> {

    @NotNull
    @NotBlank
    @Size(max = 50)
    @Column(name = "name")
    private String name;

    @NotNull
    @NotBlank
    @Size(max = 255)
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
