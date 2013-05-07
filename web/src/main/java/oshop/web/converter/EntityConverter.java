package oshop.web.converter;

import oshop.model.BaseEntity;

import java.io.Serializable;
import java.util.List;

public interface EntityConverter<T extends BaseEntity<ID>, ID extends Serializable> {

    public T convert(T entity) throws Exception;
    public List<T> convert(List<T> entities) throws Exception;
}
