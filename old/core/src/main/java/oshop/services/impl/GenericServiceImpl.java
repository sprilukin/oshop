package oshop.services.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.CriteriaImpl;
import oshop.dao.GenericDao;
import oshop.dao.GenericSearchDao;
import oshop.dto.PaginatedListImpl;
import oshop.dto.PaginatedList;
import oshop.model.BaseEntity;
import oshop.services.GenericService;
import oshop.services.filter.Filter;
import oshop.services.projection.Projection;
import oshop.services.sorter.Sorter;
import oshop.services.converter.EntityConverter;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class GenericServiceImpl<T extends BaseEntity<ID>, ID extends Serializable> implements GenericService<T, ID> {

    @Resource(name = "defaultConverter")
    private EntityConverter<T, ID> fromDTOConverter;

    @Resource
    private Filter orStringLikeFilter;

    @Resource
    private Sorter defaultSorter;

    @Resource
    private Projection projection;

    @Resource
    protected GenericSearchDao searchDao;

    protected abstract GenericDao<T, ID> getDao();

    protected abstract EntityConverter<T, ID> getToDTOConverter();

    protected EntityConverter<T, ID> getFromDTOConverter() {
        return fromDTOConverter;
    }

    protected Filter getFilter() {
        return orStringLikeFilter;
    }

    protected Sorter getSorter() {
        return defaultSorter;
    }

    protected Projection getProjection() {
        return projection;
    }

    @Override
    public T add(T entity) throws Exception {
        T domainEntity = getFromDTOConverter().convert(entity);
        getDao().add(domainEntity);
        //getDao().getSession().refresh(domainEntity);
        return getToDTOConverter().convert(domainEntity);
    }

    @Override
    public T update(T entity, ID id) throws Exception {
        entity.setId(id);
        T domainEntity = getFromDTOConverter().convert(entity);
        getDao().update(domainEntity);
        //getDao().getSession().refresh(domainEntity);
        return getToDTOConverter().convert(domainEntity);
    }

    @Override
    public void remove(ID id) throws Exception {
        getDao().remove(id);
    }

    @Override
    public T get(ID id) throws Exception {
        return getToDTOConverter().convert(getDao().get(id));
    }

    protected void applyFiltersAndSortersToCriteria(Criteria criteria, Map<String, List<String>> filters, Map<String, List<String>> sorters) {
        getFilter().apply(filters, criteria);
        getSorter().apply(sorters, criteria);
    }

    protected Criteria prepareCriteriaForCountProjection(Criteria criteria) {
        Iterator iterator = ((CriteriaImpl)criteria).iterateOrderings();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }

        return criteria.setFirstResult(0).setMaxResults(1)
                .setProjection(Projections.rowCount());
    }

    protected <K> PaginatedList<K> getCountAndPrepareListDto(List<K> list, Criteria criteria) throws Exception {

        criteria = prepareCriteriaForCountProjection(criteria);

        Number length = searchDao.get(criteria);

        return new PaginatedListImpl<K>(list, length.longValue());
    }

    @Override
    public PaginatedList<T> list(
            Map<String, List<String>> filters,
            Map<String, List<String>> sorters,
            Integer limit, Integer offset) throws Exception {

        Criteria criteria = getDao().createCriteria();
        applyFiltersAndSortersToCriteria(criteria, filters, sorters);
        List<T> list = getToDTOConverter().convert(getDao().list(criteria, offset, limit));

        return getCountAndPrepareListDto(list, criteria);
    }

    @Override
    public PaginatedList list(
            Map<String, List<String>> filters,
            Map<String, List<String>> sorters,
            Map<String, List<String>> projections,
            Integer limit, Integer offset) throws Exception {

        Criteria criteria = getDao().createCriteria();
        applyFiltersAndSortersToCriteria(criteria, filters, sorters);
        getProjection().apply(projections, criteria);

        //use List list = critelia.list(); to disregard offset and limit
        List list = searchDao.list(criteria, offset, limit);

        return getCountAndPrepareListDto(list, criteria);
    }
}
