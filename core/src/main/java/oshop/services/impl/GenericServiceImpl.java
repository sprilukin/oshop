package oshop.services.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.CriteriaImpl;
import oshop.dao.GenericDao;
import oshop.dao.GenericSearchDao;
import oshop.dto.GenericListDto;
import oshop.model.BaseEntity;
import oshop.services.GenericService;
import oshop.services.filter.Filter;
import oshop.services.sorter.Sorter;
import oshop.services.converter.EntityConverter;
import oshop.dto.GenericListDto;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class GenericServiceImpl<T extends BaseEntity<ID>, ID extends Serializable> implements GenericService<T, ID> {

    @Resource(name = "defaultConverter")
    private EntityConverter<T, ID> fromDTOConverter;

    @Resource
    private Filter defaultStringLikeFilter;

    @Resource
    private Sorter defaultSorter;

    @Resource
    protected GenericSearchDao searchDao;

    protected abstract GenericDao<T, ID> getDao();

    protected abstract EntityConverter<T, ID> getToDTOConverter();

    protected EntityConverter<T, ID> getFromDTOConverter() {
        return fromDTOConverter;
    }

    protected Filter getFilter() {
        return defaultStringLikeFilter;
    }

    protected Sorter getSorter() {
        return defaultSorter;
    }

    @Override
    public T add(T entity) throws Exception {
        ID id = getDao().add(getFromDTOConverter().convert(entity));
        getDao().getSession().clear();
        return getToDTOConverter().convert(getDao().get(id));
    }

    @Override
    public T update(T entity, ID id) throws Exception {
        entity.setId(id);
        getDao().update(getFromDTOConverter().convert(entity));
        getDao().getSession().clear();
        return getToDTOConverter().convert(getDao().get(id));
    }

    @Override
    public void remove(ID id) throws Exception {
        getDao().remove(id);
    }

    @Override
    public T get(ID id) throws Exception {
        return getToDTOConverter().convert(getDao().get(id));
    }

    protected Criteria applyFiltersAndSortersToCriteria(Criteria criteria, Map<String, List<String>> filters, Map<String, List<String>> sorters) {
        getFilter().applyFilters(filters, criteria);
        getSorter().applySorters(sorters, criteria);

        return criteria;
    }

    protected Criteria prepareCriteriaForCountProjection(Criteria criteria) {
        Iterator iterator = ((CriteriaImpl)criteria).iterateOrderings();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }

        //this is done just to clear criteria's first result and max results
        criteria.setFirstResult(0);
        criteria.setMaxResults(1);

        criteria = criteria.setProjection(Projections.rowCount());

        return criteria;
    }

    protected <K> GenericListDto<K> getCountAndPrepareListDto(List<K> list, Criteria criteria) throws Exception {

        criteria = prepareCriteriaForCountProjection(criteria);

        Number length = searchDao.get(criteria);

        return new GenericListDto<K>(list, length.longValue());
    }

    @Override
    public GenericListDto<T> list(Map<String, List<String>> filters, Map<String, List<String>> sorters, Integer limit, Integer offset) throws Exception {
        Criteria criteria = applyFiltersAndSortersToCriteria(getDao().createCriteria(), filters, sorters);
        List<T> list = getToDTOConverter().convert(getDao().list(criteria, offset, limit));

        return getCountAndPrepareListDto(list, criteria);
    }
}
