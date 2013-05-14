package oshop.model;

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
    uniqueConstraints = @UniqueConstraint(columnNames = {"description", "type", "amount"})
)
public class Discount extends BaseEntity<Integer> {

    public static enum Type {
        PERCENT_DISCOUNT((byte)0, "%"),
        FIXED_DISCOUNT((byte)1, "₴");
        //FIXED_PRICE((byte)2);

        private byte type;
        private String label;

        Type(byte type, String label) {
            this.type = type;
            this.label = label;
        }

        public byte getType() {
            return type;
        }

        public String getLabel() {
            return label;
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

    @Column(name = "type")
    private Byte type;

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
        this.amount = amount;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getTypeAsString() {
        return Type.fromByte(getType()).getLabel();
    }
}
