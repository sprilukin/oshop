package oshop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "discountType")
public class DiscountType {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;

    @Column(name = "type")
    private Byte type;

    @Column(name = "description")
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
