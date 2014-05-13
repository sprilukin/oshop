package oshop.web.api.rest.v2;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import oshop.model.BaseEntity;
import oshop.services.GenericService;
import oshop.web.api.rest.adapter.ListReturningRestCallbackAdapter;
import oshop.web.api.rest.adapter.ReturningRestCallbackAdapter;
import oshop.web.api.rest.adapter.ValidationRestCallbackAdapter;
import oshop.web.api.rest.adapter.VoidRestCallbackAdapter;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class BaseRestService<T extends BaseEntity<ID>, ID extends Serializable> {

    private static final Log log = LogFactory.getLog(BaseRestService.class);

    protected abstract GenericService<T, ID> getService();

    protected ResponseEntity<?> add(final T entity, final BindingResult result) {
        return new ValidationRestCallbackAdapter(result,
                new ReturningRestCallbackAdapter<T>() {
                    @Override
                    protected T getResult() throws Exception {
                        return getService().add(entity);
                    }
                }).invoke();
    }

    protected ResponseEntity<?> delete(final ID id) {
        return new VoidRestCallbackAdapter() {
            @Override
            protected void perform() throws Exception {
                getService().remove(id);
            }
        }.invoke();
    }

    protected ResponseEntity<?> get(final ID id) {
        return new ReturningRestCallbackAdapter<T>() {
            @Override
            protected T getResult() throws Exception {
                return getService().get(id);
            }
        }.invoke();
    }

    protected ResponseEntity<?> update(final ID id, final T entity, final BindingResult result) {

        return new ValidationRestCallbackAdapter(result,
                new ReturningRestCallbackAdapter<T>() {
                    @Override
                    protected T getResult() throws Exception {
                        return getService().update(entity, id);
                    }
                }).invoke();
    }

    protected ResponseEntity<?> list(
            final Integer limit, final Integer offset) {

        return listWithFiltersAndSorters(
                Collections.<String, List<String>>emptyMap(),
                Collections.<String, List<String>>emptyMap(), limit, offset);
    }

    protected ResponseEntity<?> listWithFiltersAndSorters(
            final Map<String, List<String>> filters,
            final Map<String, List<String>> sorters,
            final Integer limit,
            final Integer offset) {

        return new ListReturningRestCallbackAdapter<T>() {

            @Override
            protected List<T> getResult() throws Exception {
                return getService().list(filters, sorters, limit, offset);
            }
        }.invoke();
    }

    protected ResponseEntity<?> listWithFiltersSortersAndProjections(
            final Map<String, List<String>> filters,
            final Map<String, List<String>> sorters,
            final Map<String, List<String>> projections,
            final Integer limit,
            final Integer offset) {

        return new ListReturningRestCallbackAdapter() {

            @Override
            protected List getResult() throws Exception {
                return getService().list(filters, sorters, projections, limit, offset);
            }
        }.invoke();
    }
}
