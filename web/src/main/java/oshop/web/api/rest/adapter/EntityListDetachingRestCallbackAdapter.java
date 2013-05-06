package oshop.web.api.rest.adapter;

import oshop.model.BaseEntity;
import oshop.web.converter.EntityDetachConverter;

import java.io.Serializable;
import java.util.List;

public abstract class EntityListDetachingRestCallbackAdapter<T extends BaseEntity<ID>, ID extends Serializable>
        extends ListReturningRestCallbackAdapter<List<T>> {

    private EntityDetachConverter<T, ID> converter;

    protected EntityListDetachingRestCallbackAdapter(EntityDetachConverter<T, ID> converter) {
        this.converter = converter;
    }

    @Override
    protected List<T> convertList(List<T> list) throws Exception {
        return converter.detach(list);
    }
}
