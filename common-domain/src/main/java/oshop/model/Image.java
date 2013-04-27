package oshop.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "image")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Image extends BaseEntity<Integer> {

    @NotNull
    @Lob
    @Column(length = 1048576)
    private byte[] data;

    @NotNull
    //@Pattern(regexp = "^image/[\\w]+$")
    private String contentType;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
