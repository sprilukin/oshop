package oshop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "image")
public class Image extends BaseEntity<Integer> {

    @NotNull
    @Lob
    @Column(length = 1048576)
    private byte[] data;

    @NotNull
    //@Pattern(regexp = "^image/[\\w]+$")
    @Column(name = "content_type")
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
