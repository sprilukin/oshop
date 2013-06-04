package oshop.dto;

import java.util.List;

public interface PaginatedCollectionList<T> extends List<T> {
    public Long getTotalCollectionSize();
}
