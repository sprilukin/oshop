package oshop.web.api.rest.adapter;

import oshop.model.BaseEntity;
import oshop.web.converter.EntityConverter;

import java.io.Serializable;

public abstract class EntityDetachingRestCallbackAdapter<T extends BaseEntity<ID>, ID extends Serializable>
        extends ReturningRestCallbackAdapter<T> {

    private EntityConverter<T, ID> converter;

    protected EntityDetachingRestCallbackAdapter(EntityConverter<T, ID> converter) {
        this.converter = converter;
    }

    @Override
    protected T convertResult(T result) throws Exception {
        return converter.convert(result);
    }
}