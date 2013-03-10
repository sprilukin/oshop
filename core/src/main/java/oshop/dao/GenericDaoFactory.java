package oshop.dao;

import org.springframework.stereotype.Component;
import oshop.model.BaseEntity;

import java.io.Serializable;

@Component
public class GenericDaoFactory {

    public <T extends BaseEntity<ID>, ID extends Serializable> GenericDao<T, ID> getGenericDao(Class<T> entityClass) {
        return new GenericDaoImpl<T, ID>(entityClass);
    }
}
