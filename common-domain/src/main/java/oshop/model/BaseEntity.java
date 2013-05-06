package oshop.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseEntity<ID extends Serializable> {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private ID id;

    @Version
    @Column(name = "version")
    private Long version = 0L;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastUpdate")
    private Date lastUpdate;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
