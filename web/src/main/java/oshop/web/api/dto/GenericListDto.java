package oshop.web.api.dto;

import java.util.Collection;


public class GenericListDto<T extends Collection<K>, K> {

    private T list;
    private Integer size;

    public GenericListDto(T list, Integer size) {
        this.list = list;
        this.size = size;
    }

    public T getList() {
        return list;
    }

    public void setList(T list) {
        this.list = list;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
