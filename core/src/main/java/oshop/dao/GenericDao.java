package oshop.dao;

import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import oshop.model.BaseEntity;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

public interface GenericDao<T extends BaseEntity<ID>, ID extends Serializable> {

    public Criteria createCriteria();

    public List<T> findAll(Criteria criteria);

    public T get(ID id);

    public T findUnique(Criteria criteria);

    public List<T> list(Integer page, Integer limit);

    public List<T> list(Criteria criteria, Integer page, Integer limit);

    public ID add(T entity);

    public void update(T entity);

    public void remove(ID id);

    public void remove(T entity);
}