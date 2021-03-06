package oshop.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;
import oshop.model.BaseEntity;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

@Transactional(readOnly = true)
public interface GenericDao<T extends BaseEntity<ID>, ID extends Serializable> {

    @Transactional(readOnly = false)
    public void executeQuery(String query, QueryManipulator queryManipulator);

    @Transactional(readOnly = false)
    public void executeQuery(String query, SQLQueryManipulator queryManipulator);

    public Criteria createCriteria();

    public T get(ID id);

    public T get(Criteria criteria);

    public List<T> list(Criteria criteria);

    public List<T> list(Integer page, Integer limit);

    public List<T> list(Criteria criteria, Integer page, Integer limit);

    public List<T> list(Query query, Integer page, Integer limit);

    @Transactional(readOnly = false)
    public ID add(@Valid T entity);

    @Transactional(readOnly = false)
    public void update(@Valid T entity);

    @Transactional(readOnly = false)
    public void remove(ID id);

    @Transactional(readOnly = false)
    public void remove(@Valid T entity);

    @Transactional(readOnly = false)
    public Session getSession();

    interface QueryManipulator {
        public void manipulateWithQuery(Query query);
    }

    interface SQLQueryManipulator {
        public void manipulateWithQuery(SQLQuery query);
    }
}