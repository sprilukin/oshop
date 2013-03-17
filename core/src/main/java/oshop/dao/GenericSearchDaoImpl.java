package oshop.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import oshop.model.BaseEntity;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class GenericSearchDaoImpl implements GenericSearchDao {

    @Value("${list.limit}")
    private Integer listLimit = 1000;

    @Resource
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public <T extends BaseEntity> Criteria createCriteria(Class<T> entityClass) {
        return getSession().createCriteria(entityClass);
    }

    @SuppressWarnings("unchecked") //Developer responsible for correct type check
    public <T> T get(Criteria criteria) {
        return (T)criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked") //Developer responsible for correct type check
    public <T> List<T> list(Criteria criteria, Integer page, Integer limit) {
        int safePageNumber = page != null ? page : 0;
        int safeLimitNumber = (limit != null && limit > 0) ? limit : listLimit;

        criteria.setMaxResults(safeLimitNumber).setFirstResult(safePageNumber * safeLimitNumber);

        return (List<T>)criteria.list();
    }
}