package oshop.dto;

import java.util.*;

public class PaginatedListImpl<T> extends ArrayList<T> implements PaginatedList<T> {

    private Long totalSize;

    public PaginatedListImpl(Collection<? extends T> c, Long totalSize) {
        super(c);
        this.totalSize = totalSize;
    }

    @Override
    public Long getTotalSize() {
        return totalSize;
    }
}
