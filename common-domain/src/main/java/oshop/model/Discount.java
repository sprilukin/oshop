package oshop.model;

import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(
    name = "discount",
    uniqueConstraints = @UniqueConstraint(
            name = "UK_description_type_amount",
            columnNames = {"description", "type", "amount"}
    )
)
public class Discount extends BaseEntity<Integer> {

    public static enum Type {
        PERCENT_DISCOUNT((byte)0),
        FIXED_DISCOUNT((byte)1);
        //FIXED_PRICE((byte)2);

        private byte type;

        Type(byte type) {
            this.type = type;
        }

        public byte getType() {
            return type;
        }

        public static Type fromByte(byte value) {
            for (Type type: Type.values()) {
                if (type.getType() == value) {
                    return type;
                }
            }

            return null;
        }
    }

    @NotNull
    @NotBlank
    @Size(max = 255)
    @Column(name = "description")
    private String description;

    @Column(name = "type", nullable = false)
    private Byte type = Type.FIXED_DISCOUNT.getType();

    @Column(name = "amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal amount;

    @Formula("( SELECT CASE WHEN d.type = 0 THEN '%' WHEN d.type = 1 THEN '₴' END FROM discount d where d.id = id )")
    private String typeAsString;

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
        this.amount = amount;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getTypeAsString() {
        return typeAsString;
    }

    public void setTypeAsString(String typeAsString) {
        this.typeAsString = typeAsString;
    }
}
