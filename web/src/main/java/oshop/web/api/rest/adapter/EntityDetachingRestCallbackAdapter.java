package oshop.web.api.rest.adapter;

import oshop.model.BaseEntity;
import oshop.web.converter.EntityDetachConverter;

import java.io.Serializable;

public abstract class EntityDetachingRestCallbackAdapter<T extends BaseEntity<ID>, ID extends Serializable>
        extends ReturningRestCallbackAdapter<T> {

    private EntityDetachConverter<T, ID> converter;

    protected EntityDetachingRestCallbackAdapter(EntityDetachConverter<T, ID> converter) {
        this.converter = converter;
    }

    @Override
    protected T convertResult(T result) throws Exception {
        return converter.detach(result);
    }
}
