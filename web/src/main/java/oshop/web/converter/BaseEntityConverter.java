package oshop.web.converter;

import oshop.model.BaseEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseEntityConverter<T extends BaseEntity<ID>, ID extends Serializable>
        implements EntityConverter<T, ID> {

    protected abstract Class<T> entityClass();
    protected abstract void convert(T entity, T convertedEntity) throws Exception;

    protected T newInstance()  throws Exception {
        return entityClass().newInstance();
    }

    @Override
    public T convert(T entity)  throws Exception {
        if (entity == null) {
            return null;
        }

        T convertedEntity = newInstance();
        convertedEntity.setId(entity.getId());
        convertedEntity.setLastUpdate(entity.getLastUpdate());
        convertedEntity.setVersion(entity.getVersion());

        convert(entity, convertedEntity);

        return convertedEntity;
    }

    protected T convertForList(T entity) throws Exception {
        return convert(entity);
    }

    @Override
    public List<T> convert(List<T> entities)  throws Exception {
        if (entities == null) {
            return null;
        }

        List<T> convertedList = new ArrayList<T>(entities.size());

        for (T entity: entities) {
            convertedList.add(convertForList(entity));
        }

        return convertedList;
    }
}
