package oshop.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "album")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Album extends BaseEntity<Integer> {

    @NotNull
    @Size(min = 1)
    @Column(name = "name")
    private String name;

    @NotNull
    @Size(min = 1)
    @Column(name = "description")
    private String surname;

    @ManyToOne(fetch = FetchType.LAZY)
    private Discount discount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }
}
