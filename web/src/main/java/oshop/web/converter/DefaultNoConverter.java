package oshop.web.converter;

import oshop.model.BaseEntity;

import java.io.Serializable;
import java.util.List;

public class DefaultNoConverter<T extends BaseEntity<ID>, ID extends Serializable>
        implements EntityConverter<T, ID> {

    @Override
    public T convert(T entity)  throws Exception {
        return entity;
    }

    @Override
    public List<T> convert(List<T> entities)  throws Exception {
        return entities;
    }
}
