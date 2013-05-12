package oshop.web.api.rest.adapter;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.CriteriaImpl;
import oshop.dao.GenericSearchDao;
import oshop.model.BaseEntity;
import oshop.web.converter.EntityConverter;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public abstract class EntityListDetachingRestCallbackAdapter<T extends BaseEntity<ID>, ID extends Serializable>
        extends ListReturningRestCallbackAdapter<List<T>> {

    private EntityConverter<T, ID> converter;
    private GenericSearchDao searchDao;

    protected EntityListDetachingRestCallbackAdapter(EntityConverter<T, ID> converter, GenericSearchDao searchDao) {
        this.converter = converter;
        this.searchDao = searchDao;
    }

    @Override
    protected List<T> convertList(List<T> list) throws Exception {
        return converter.convert(list);
    }

    @Override
    protected Long getSize() throws Exception {
        CriteriaImpl criteria = (CriteriaImpl)getCriteria();

        Iterator iterator = criteria.iterateOrderings();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }

        criteria.setProjection(Projections.rowCount());

        return searchDao.get(criteria);
    }

    @Override
    protected List<T> getList() throws Exception {
        return getList(getCriteria());
    }

    protected abstract Criteria getCriteria();
    protected abstract List<T> getList(Criteria criteria);
}
