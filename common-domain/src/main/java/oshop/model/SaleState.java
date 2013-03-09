package oshop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "saleState")
public class SaleState {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;

    @Column(name = "state")
    private Byte state;

    @Column(name = "name")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static enum State {
        NEW((byte)0),
        RESERVED((byte)1),
        MONEY_RECEIVED((byte)2),
        SENT((byte)3),
        DELIVERED((byte)4),
        CANCELED((byte)5);

        private byte type;

        State(byte type) {
            this.type = type;
        }

        public byte getType() {
            return type;
        }

        public static State fromByte(byte value) {
            for (State type: State.values()) {
                if (type.getType() == value) {
                    return type;
                }
            }

            return null;
        }
    }
}
