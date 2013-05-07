package oshop.web.converter;

import oshop.model.BaseEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseEntityConverter<T extends BaseEntity<ID>, ID extends Serializable>
        implements EntityConverter<T, ID> {

    protected abstract Class<T> entityClass();
    protected abstract void convert(T entity, T convertedEntity) throws Exception;

    @Override
    public T convert(T entity)  throws Exception {
        if (entity == null) {
            return null;
        }

        T convertedEntity = entityClass().newInstance();
        convertedEntity.setId(entity.getId());
        convertedEntity.setLastUpdate(entity.getLastUpdate());
        convertedEntity.setVersion(entity.getVersion());

        convert(entity, convertedEntity);

        return convertedEntity;
    }

    @Override
    public List<T> convert(List<T> entities)  throws Exception {
        if (entities == null) {
            return null;
        }

        List<T> convertedList = new ArrayList<T>(entities.size());

        for (T entity: entities) {
            convertedList.add(convert(entity));
        }

        return convertedList;
    }
}
