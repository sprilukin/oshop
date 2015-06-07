package oshop.dao;

import org.hibernate.Criteria;
import org.springframework.transaction.annotation.Transactional;
import oshop.model.BaseEntity;

import java.util.List;

@Transactional(readOnly = true)
public interface GenericSearchDao {

    public <T extends BaseEntity> Criteria createCriteria(Class<T> entityClass);

    public <T> T get(Criteria criteria);

    public <T> List<T> list(Criteria criteria, Integer page, Integer limit);
}