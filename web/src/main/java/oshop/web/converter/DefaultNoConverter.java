package oshop.web.converter;

import org.springframework.stereotype.Component;
import oshop.model.BaseEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component("defaultConverter")
public class DefaultNoConverter<T extends BaseEntity<ID>, ID extends Serializable>
        implements EntityConverter<T, ID> {

    @Override
    public T convert(T entity)  throws Exception {
        return entity;
    }

    @Override
    public List<T> convertList(Collection<T> entities)  throws Exception {
        return (List<T>)entities;
    }


    @Override
    public Set<T> convertSet(Collection<T> entities) throws Exception {
        return (Set<T>)entities;
    }
}
