package oshop.dto;

import java.util.*;

public class ListWithTotalSize<T> extends ArrayList<T> implements PaginatedCollectionList<T> {

    private Long totalSize;

    public ListWithTotalSize(Collection<? extends T> c, Long totalSize) {
        super(c);
        this.totalSize = totalSize;
    }

    @Override
    public Long getTotalCollectionSize() {
        return totalSize;
    }
}
