package oshop.web.api.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import oshop.model.BaseEntity;
import oshop.services.GenericService;
import oshop.web.api.rest.adapter.ListReturningRestCallbackAdapter;
import oshop.web.api.rest.adapter.ReturningRestCallbackAdapter;
import oshop.web.api.rest.adapter.ValidationRestCallbackAdapter;
import oshop.web.api.rest.adapter.VoidRestCallbackAdapter;
import oshop.dto.GenericListDto;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class BaseController<T extends BaseEntity<ID>, ID extends Serializable> {

    private static final Log log = LogFactory.getLog(BaseController.class);

    protected abstract GenericService<T, ID> getService();

    @RequestMapping(
            value = "/",
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> add(@RequestBody @Valid final T entity, final BindingResult result) {
        return new ValidationRestCallbackAdapter(result,
                new ReturningRestCallbackAdapter<T>() {
                    @Override
                    protected T getResult() throws Exception {
                        return getService().add(entity);
                    }
                }).invoke();
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable final ID id) {
        return new VoidRestCallbackAdapter() {
            @Override
            protected void perform() throws Exception {
                getService().remove(id);
            }
        }.invoke();
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable final ID id) {
        return new ReturningRestCallbackAdapter<T>() {
            @Override
            protected T getResult() throws Exception {
                return getService().get(id);
            }
        }.invoke();
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> update(
            @PathVariable final ID id,
            @RequestBody @Valid final T entity, final BindingResult result) {

        return new ValidationRestCallbackAdapter(result,
                new ReturningRestCallbackAdapter<T>() {
                    @Override
                    protected T getResult() throws Exception {
                        return getService().update(entity, id);
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

        return listWithFiltersAndSorters(
                Collections.<String, List<String>>emptyMap(),
                Collections.<String, List<String>>emptyMap(), limit, offset);
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

        return new ListReturningRestCallbackAdapter<T>() {

            @Override
            protected GenericListDto<T> getListDto() throws Exception {
                return getService().list(filters, sorters, limit, offset);
            }
        }.invoke();
    }
}
