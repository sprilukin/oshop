package oshop.web.converter;

import oshop.model.BaseEntity;

import java.io.Serializable;
import java.util.List;

public interface EntityDetachConverter<T extends BaseEntity<ID>, ID extends Serializable> {

    public T detach(T entity) throws Exception;
    public List<T> detach(List<T> entities) throws Exception;
}
