package oshop.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = "prepayment")
public class Prepayment extends BaseEntity<Integer> {

    @NotNull
    @NotBlank
    @Size(max = 255)
    @Column(name = "description", unique = true)
    private String description;

    @Min(0)
    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = EntityUtils.round(amount, 2);
    }
}
