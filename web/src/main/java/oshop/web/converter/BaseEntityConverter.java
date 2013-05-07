package oshop.web.converter;

import oshop.model.BaseEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseEntityConverter<T extends BaseEntity<ID>, ID extends Serializable>
        implements EntityConverter<T, ID> {

    protected abstract Class<T> entityClass();
    protected abstract void detach(T entity, T detachedEntity) throws Exception;

    @Override
    public T convert(T entity)  throws Exception {
        if (entity == null) {
            return null;
        }

        T detachedEntity = entityClass().newInstance();
        detachedEntity.setId(entity.getId());
        detachedEntity.setLastUpdate(entity.getLastUpdate());
        detachedEntity.setVersion(entity.getVersion());

        detach(entity, detachedEntity);

        return detachedEntity;
    }

    @Override
    public List<T> convert(List<T> entities)  throws Exception {
        if (entities == null) {
            return null;
        }

        List<T> detachedList = new ArrayList<T>(entities.size());

        for (T entity: entities) {
            detachedList.add(convert(entity));
        }

        return detachedList;
    }
}
