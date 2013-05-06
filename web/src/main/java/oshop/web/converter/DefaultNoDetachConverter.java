package oshop.web.converter;

import oshop.model.BaseEntity;

import java.io.Serializable;
import java.util.List;

public class DefaultNoDetachConverter<T extends BaseEntity<ID>, ID extends Serializable>
        implements EntityDetachConverter<T, ID> {

    @Override
    public T detach(T entity)  throws Exception {
        return entity;
    }

    @Override
    public List<T> detach(List<T> entities)  throws Exception {
        return entities;
    }
}
