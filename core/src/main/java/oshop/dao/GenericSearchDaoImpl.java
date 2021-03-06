package oshop.dao;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Repository;
import oshop.dao.exception.NotFoundException;
import oshop.model.BaseEntity;

import javax.annotation.Resource;
import java.util.List;

@Repository("searchDao")
public class GenericSearchDaoImpl implements GenericSearchDao {

    @Value("${list.limit}")
    private Integer listLimit = 1000;

    @Resource
    private SessionFactory sessionFactory;

    @Resource
    private MessageSource messageSource;

    private Session getSession() {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.setFlushMode(FlushMode.MANUAL);
        return currentSession;
    }

    public <T extends BaseEntity> Criteria createCriteria(Class<T> entityClass) {
        return getSession().createCriteria(entityClass);
    }

    @SuppressWarnings("unchecked") //Developer responsible for correct type check
    public <T> T get(Criteria criteria) {
        T result = (T)criteria.uniqueResult();
        if (result == null) {
            throw new NotFoundException(
                    messageSource.getMessage("error.not.found", null, LocaleContextHolder.getLocale()));
        } else {
            return result;
        }
    }

    @SuppressWarnings("unchecked") //Developer responsible for correct type check
    public <T> List<T> list(Criteria criteria, Integer page, Integer limit) {
        int safePageNumber = page != null ? page : 0;
        int safeLimitNumber = (limit != null && limit > 0) ? limit : listLimit;

        criteria.setMaxResults(safeLimitNumber).setFirstResult(safePageNumber * safeLimitNumber);

        return (List<T>)criteria.list();
    }
}