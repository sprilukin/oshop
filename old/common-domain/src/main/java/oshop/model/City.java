package oshop.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(
        name = "city",
        uniqueConstraints = @UniqueConstraint(
                name = "UK_name_region",
                columnNames = {"name", "region"}
        )
)
public class City extends BaseEntity<Integer> {

    @NotNull
    @NotBlank
    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @NotNull
    @NotBlank
    @Size(max = 255)
    @Column(name = "region")
    private String region;

    @Size(max = 255)
    @Column(name = "latitude")
    private String latitude;

    @Size(max = 255)
    @Column(name = "longitude")
    private String longitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
