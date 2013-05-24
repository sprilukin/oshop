package oshop.web.api.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import oshop.dao.GenericDao;
import oshop.dao.GenericSearchDao;
import oshop.model.BaseEntity;
import oshop.web.api.rest.adapter.EntityDetachingRestCallbackAdapter;
import oshop.web.api.rest.adapter.EntityListDetachingRestCallbackAdapter;
import oshop.web.api.rest.adapter.ValidationRestCallbackAdapter;
import oshop.web.api.rest.adapter.VoidRestCallbackAdapter;
import oshop.web.converter.EntityConverter;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Transactional(readOnly = true)
public abstract class BaseController<T extends BaseEntity<ID>, ID extends Serializable> {

    private static final Log log = LogFactory.getLog(BaseController.class);

    public static final String ASC_SORT = "asc";
    public static final String DESC_SORT = "desc";

    @Resource
    private GenericSearchDao searchDao;

    @Resource(name = "defaultConverter")
    private EntityConverter<T, ID> fromDTOConverter;

    protected GenericSearchDao getSearchDao() {
        return searchDao;
    }

    protected abstract GenericDao<T, ID> getDao();

    protected abstract EntityConverter<T, ID> getToDTOConverter();

    protected EntityConverter<T, ID> getFromDTOConverter() {
        return fromDTOConverter;
    }

    protected void applyFilters(Map<String, List<String>> filters, Criteria criteria) {
        Disjunction disjunction = Restrictions.disjunction();
        for (Map.Entry<String, List<String>> entry: filters.entrySet()) {
            disjunction.add(getRestrictionForFilter(entry.getKey(), entry.getValue(), criteria));
        }

        criteria.add(disjunction);
    }

    protected Criterion getRestrictionForFilter(String column, List<String> values, Criteria criteria) {
        Conjunction conjunction = Restrictions.conjunction();
        for (String likeExpression: values) {
            conjunction.add(Restrictions.like(column, likeExpression, MatchMode.ANYWHERE));
        }

        return conjunction;
    }

    protected void applySorters(Map<String, List<String>> sorters, Criteria criteria) {
        for (Map.Entry<String, List<String>> entry: sorters.entrySet()) {
            String fieldName = entry.getKey();
            String sortType = entry.getValue().get(0);

            addOrder(fieldName, sortType, criteria);
        }
    }

    protected void addOrder(String column, String order, Criteria criteria) {
        if (ASC_SORT.equalsIgnoreCase(order)) {
            criteria.addOrder(Order.asc(column));
        } else if (DESC_SORT.equals(order)) {
            criteria.addOrder(Order.desc(column));
        }
    }

    @RequestMapping(
            value = "/",
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Transactional(readOnly = false)
    public ResponseEntity<?> add(@RequestBody @Valid final T entity, final BindingResult result) {
        return new ValidationRestCallbackAdapter(result,
                new EntityDetachingRestCallbackAdapter<T, ID>(getToDTOConverter()) {
                    @Override
                    protected T getResult() throws Exception {
                        ID id = getDao().add(getFromDTOConverter().convert(entity));
                        getDao().getSession().clear();
                        return getDao().get(id);
                    }
                }).invoke();
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE)
    @Transactional(readOnly = false)
    public ResponseEntity<?> delete(@PathVariable final ID id) {
        return new VoidRestCallbackAdapter() {
            @Override
            protected void perform() throws Exception {
                getDao().remove(id);            }
        }.invoke();
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable final ID id) {
        return new EntityDetachingRestCallbackAdapter<T, ID>(getToDTOConverter()) {
            @Override
            protected T getResult() throws Exception {
                return getDao().get(id);
            }
        }.invoke();
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Transactional(readOnly = false)
    public ResponseEntity<?> update(
            @PathVariable final ID id,
            @RequestBody @Valid final T entity, final BindingResult result) {

        return new ValidationRestCallbackAdapter(result,
                new EntityDetachingRestCallbackAdapter<T, ID>(getToDTOConverter()) {
                    @Override
                    protected T getResult() throws Exception {
                        entity.setId(id);
                        getDao().update(getFromDTOConverter().convert(entity));
                        getDao().getSession().clear();
                        return getDao().get(id);
                    }
                }).invoke();
    }

    @RequestMapping(
            value = "/",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> list(
            @RequestParam(value = "limit", required = false) final Integer limit,
            @RequestParam(value = "offset", required = false) final Integer offset) {

        return new EntityListDetachingRestCallbackAdapter<T, ID>(getToDTOConverter(), searchDao) {

            @Override
            protected Criteria getCriteria() {
                return getDao().createCriteria();
            }

            @Override
            protected List<T> getList(Criteria criteria) {
                return getDao().list(criteria, offset, limit);
            }
        }.invoke();
    }

    @RequestMapping(
            value = "/{filter}/{sort}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> listWithFiltersAndSorters(
            @MatrixVariable(pathVar="filter", required = false) final Map<String, List<String>> filters,
            @MatrixVariable(pathVar="sort", required = false) final Map<String, List<String>> sorters,
            @RequestParam(value = "limit", required = false) final Integer limit,
            @RequestParam(value = "offset", required = false) final Integer offset) {

        return new EntityListDetachingRestCallbackAdapter<T, ID>(getToDTOConverter(), searchDao) {

            @Override
            protected Criteria getCriteria() {
                Criteria criteria = getDao().createCriteria();
                applyFilters(filters, criteria);
                applySorters(sorters, criteria);

                return criteria;
            }

            @Override
            protected List<T> getList(Criteria criteria) {
                return getDao().list(criteria, offset, limit);
            }
        }.invoke();
    }
}
