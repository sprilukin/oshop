package oshop.dto;

import java.util.List;

public interface PaginatedList<T> extends List<T> {
    public Long getTotalSize();
}
