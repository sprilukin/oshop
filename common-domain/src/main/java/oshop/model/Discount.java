package oshop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "discount")
public class Discount extends BaseEntity<Integer> {

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private Byte type;

    @Column(name = "amount")
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

    public static enum Type {
        PERCENT_DISCOUNT((byte)0),
        FIXED_DISCOUNT((byte)1),
        FIXED_PRICE((byte)2);

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
}
