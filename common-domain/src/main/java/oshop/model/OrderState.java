package oshop.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "order_state")
public class OrderState extends BaseEntity<Integer> {

    @NotNull
    @NotBlank
    @Size(max = 255)
    @Column(name = "name", unique = true)
    private String name;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
