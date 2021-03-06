package oshop.dao;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import oshop.dao.exception.NotFoundException;
import oshop.model.BaseEntity;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class GenericDaoImpl<T extends BaseEntity<ID>, ID extends Serializable> implements GenericDao<T, ID> {

    @Resource
    private SessionFactory sessionFactory;

    @Resource
    private MessageSource messageSource;

    private Class<T> entityClass;
    private Integer listLimit = 1000;

    public GenericDaoImpl() {
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void setListLimit(Integer listLimit) {
        this.listLimit = listLimit;
    }

    public Session getSession() {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.setFlushMode(FlushMode.AUTO);
        return currentSession;
    }

    private Session getReadOnlySession() {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.setFlushMode(FlushMode.MANUAL);
        return currentSession;
    }

    @Override
    public void executeQuery(String queryString, QueryManipulator queryManipulator) {
        Query query = getSession().createQuery(queryString);
        if (queryManipulator != null) {
            queryManipulator.manipulateWithQuery(query);
        }
        query.executeUpdate();
    }

    @Override
    public void executeQuery(String queryString, SQLQueryManipulator queryManipulator) {
        SQLQuery query = getSession().createSQLQuery(queryString);
        if (queryManipulator != null) {
            queryManipulator.manipulateWithQuery(query);
        }

        query.executeUpdate();
    }

    public Criteria createCriteria() {
        return getReadOnlySession().createCriteria(entityClass);
    }

    @SuppressWarnings("unchecked")
    public List<T> list(Criteria criteria) {
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    public T get(ID id) {
        T entity = (T) getReadOnlySession().get(entityClass, id);
        if (entity != null) {
            return entity;
        } else {
            throw new NotFoundException(
                    messageSource.getMessage("error.entity.by.id.not.found",
                            new Object[]{id}, LocaleContextHolder.getLocale()));
        }
    }

    @SuppressWarnings("unchecked")
    public T get(Criteria criteria) {
        T entity = (T)criteria.uniqueResult();
        if (entity != null) {
            return entity;
        } else {
            throw new NotFoundException(
                    messageSource.getMessage("error.entity.not.found",
                            new Object[]{entityClass}, LocaleContextHolder.getLocale()));
        }
    }

    public List<T> list(Integer page, Integer limit) {
        return list((Criteria)null, page, limit);
    }

    public List<T> list(Criteria criteria, Integer page, Integer limit) {
        if (criteria == null) {
            criteria = createCriteria();
        }

        int safePageNumber = page != null ? page : 0;
        int safeLimitNumber = (limit != null && limit > 0) ? limit : listLimit;

        criteria.setMaxResults(safeLimitNumber).setFirstResult(safePageNumber * safeLimitNumber);

        return list(criteria);
    }

    public List<T> list(Query query, Integer page, Integer limit) {
        int safePageNumber = page != null ? page : 0;
        int safeLimitNumber = (limit != null && limit > 0) ? limit : listLimit;

        query.setMaxResults(safeLimitNumber).setFirstResult(safePageNumber * safeLimitNumber);

        @SuppressWarnings("unchecked") //Developer is responsible to return correct type by HQL
        List<T> list = (List<T>) query.list();

        return list;
    }

    @SuppressWarnings("unchecked")
    public ID add(T entity) {
        if (entity.getId() != null) {
            throw new IllegalStateException("Id should be empty for new object");
        }

        entity.setLastUpdate(new Date());
        ID id = (ID) getSession().save(entity);
        getSession().flush();
        return id;
    }

    public void update(T entity) {
        entity.setLastUpdate(new Date());
        getSession().update(getSession().merge(entity));
        getSession().flush();
    }

    public void remove(ID id) {
        T entity = get(id);
        if (entity == null) {
            throw new NotFoundException(
                    messageSource.getMessage("error.entity.by.id.not.found",
                            new Object[]{id}, LocaleContextHolder.getLocale()));
        }

        remove(entity);
    }

    public void remove(T entity) {
        getSession().delete(entity);
    }
}