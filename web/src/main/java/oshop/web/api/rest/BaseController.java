package oshop.web.api.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
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
import oshop.web.converter.EntityDetachConverter;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Transactional(readOnly = true)
public abstract class BaseController<T extends BaseEntity<ID>, ID extends Serializable> {

    private static final Log log = LogFactory.getLog(BaseController.class);

    protected abstract GenericDao<T, ID> getDao();

    protected abstract EntityDetachConverter<T, ID> getDefaultConverter();

    @Resource
    private GenericSearchDao searchDao;

    protected GenericSearchDao getSearchDao() {
        return searchDao;
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
                new EntityDetachingRestCallbackAdapter<T, ID>(getDefaultConverter()) {
                    @Override
                    protected T getResult() throws Exception {
                        ID id = getDao().add(entity);
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
        return new EntityDetachingRestCallbackAdapter<T, ID>(getDefaultConverter()) {
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
                new EntityDetachingRestCallbackAdapter<T, ID>(getDefaultConverter()) {
                    @Override
                    protected T getResult() throws Exception {
                        entity.setId(id);
                        getDao().update(entity);
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

        return new EntityListDetachingRestCallbackAdapter<T, ID>(getDefaultConverter()) {

            @Override
            protected Long getSize() throws Exception {
                return searchDao.get(getDao().createCriteria().setProjection(Projections.rowCount()));
            }

            @Override
            protected List<T> getList() throws Exception {
                return getDao().list(getDao().createCriteria(), offset, limit);
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

        return new EntityListDetachingRestCallbackAdapter<T, ID>(getDefaultConverter()) {

            @Override
            protected Long getSize() throws Exception {
                Criteria criteria = getDao().createCriteria();
                ControllerUtils.applyFilters(filters, criteria);

                criteria.setProjection(Projections.rowCount());
                return searchDao.get(criteria);
            }

            @Override
            protected List<T> getList() throws Exception {
                Criteria criteria = getDao().createCriteria();
                ControllerUtils.applyFilters(filters, criteria);
                ControllerUtils.applySorters(sorters, criteria);

                return getDao().list(criteria, offset, limit);
            }
        }.invoke();
    }
}
