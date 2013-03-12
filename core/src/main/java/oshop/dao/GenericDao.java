package oshop.dao;

import org.hibernate.Criteria;
import org.springframework.transaction.annotation.Transactional;
import oshop.model.BaseEntity;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

@Transactional(readOnly = true)
public interface GenericDao<T extends BaseEntity<ID>, ID extends Serializable> {

    public Criteria createCriteria();

    public List<T> findAll(Criteria criteria);

    public T get(ID id);

    public T findUnique(Criteria criteria);

    public List<T> list(Integer page, Integer limit);

    public List<T> list(Criteria criteria, Integer page, Integer limit);

    @Transactional(readOnly = false)
    public ID add(@Valid T entity);

    @Transactional(readOnly = false)
    public void update(@Valid T entity);

    @Transactional(readOnly = false)
    public void remove(ID id);

    @Transactional(readOnly = false)
    public void remove(@Valid T entity);
}