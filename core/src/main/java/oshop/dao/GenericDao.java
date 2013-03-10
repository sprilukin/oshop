package oshop.dao;

import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import oshop.model.BaseEntity;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

public abstract class GenericDao<T extends BaseEntity<ID>, ID extends Serializable> {

    @Resource
    private SessionFactory sessionFactory;

    protected abstract Class<T> getDomainClass();

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    protected Criteria createCriteria() {
        return getSession().createCriteria(getDomainClass());
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll(Criteria criteria) {
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    public T get(ID id) {
        return (T) getSession().get(getDomainClass(), id);
    }

    @SuppressWarnings("unchecked")
    protected T findUnique(Criteria criteria) {
        return (T)criteria.uniqueResult();
    }

    public List<T> list(Integer page, Integer limit) {
        return list(null, page, limit);
    }

    public List<T> list(Criteria criteria, Integer page, Integer limit) {
        if (criteria == null) {
            criteria = createCriteria();
        }

        int safePageNumber = page != null ? page : 0;
        int safeLimitNumber = (limit != null && limit > 0) ? limit : Integer.MAX_VALUE;

        criteria.setMaxResults(safeLimitNumber).setFirstResult(safePageNumber * safeLimitNumber);

        return findAll(criteria);
    }

    @SuppressWarnings("unchecked")
    public ID add(T entity) {
        if (entity.getId() != null) {
            throw new IllegalStateException("Id should be empty for new object");
        }

        return (ID)getSession().save(entity);
    }

    public void update(T entity) {
        getSession().update(entity);
    }

    public void remove(ID id) {
        T entity = get(id);
        if (entity != null) {
            throw new ObjectNotFoundException(id, getClass().getName());
        }

        remove(entity);
    }

    public void remove(T entity) {
        getSession().delete(entity);
    }
}