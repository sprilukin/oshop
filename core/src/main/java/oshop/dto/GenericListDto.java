package oshop.dto;

import java.util.Collection;

public class GenericListDto<T> {

    private Collection<T> list;

    private Long size;

    public GenericListDto(Collection<T> list, Long size) {
        this.list = list;
        this.size = size;
    }

    public Collection<T> getList() {
        return list;
    }

    public void setList(Collection<T> list) {
        this.list = list;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}

