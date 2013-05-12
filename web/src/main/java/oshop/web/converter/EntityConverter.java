package oshop.web.converter;

import oshop.model.BaseEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface EntityConverter<T extends BaseEntity<ID>, ID extends Serializable> {

    public T convert(T entity) throws Exception;
    public List<T> convertList(Collection<T> entities) throws Exception;
    public Set<T> convertSet(Collection<T> entities) throws Exception;
}
