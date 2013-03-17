package oshop.web.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;


public class GenericListDto<T extends Collection> {

    @JsonProperty("values")
    private T list;

    @JsonProperty("size")
    private Number size;

    public GenericListDto(T list, Number size) {
        this.list = list;
        this.size = size;
    }

    public T getList() {
        return list;
    }

    public void setList(T list) {
        this.list = list;
    }

    public Number getSize() {
        return size;
    }

    public void setSize(Number size) {
        this.size = size;
    }
}
