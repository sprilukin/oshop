package oshop.web.api.rest.v2;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import oshop.model.City;
import oshop.services.GenericService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/v2")
public class CItiesRestService extends BaseRestService<City, Integer> {

    @Resource
    private GenericService<City, Integer> cityService;

    @Override
    protected GenericService<City, Integer> getService() {
        return cityService;
    }

    /* ===== Model ===== */

    /**
     * Delete city
     *
     * @param id the id of the city to remove
     * @return nothing
     */
    @RequestMapping(
            value = "/cities/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable final Integer id) {
        return super.delete(id);
    }

    /**
     * Get city by id
     *
     * @param id the id of the city
     * @return city
     */
    @RequestMapping(
            value = "/cities/{id}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable final Integer id) {
        return super.get(id);
    }

    /**
     * Update city with given id
     *
     * @param id the id of the city
     * @return city with updates
     */
    @RequestMapping(
            value = "/cities/{id}",
            method = RequestMethod.PUT,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> update(
            @PathVariable final Integer id,
            @RequestBody @Valid final City entity, final BindingResult result) {

        return super.update(id, entity, result);
    }

    /* ===== Collection ===== */

    /**
     * Add new city
     *
     * @param entity city
     * @param result binding result
     * @return newly created city
     */
    @RequestMapping(
            value = "/cities",
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> add(@RequestBody @Valid final City entity, final BindingResult result) {
        return super.add(entity, result);
    }

    /**
     * List cities
     *
     * @param limit max results count
     * @param offset offset from the beginning of the collection
     * @return cities
     */
    @RequestMapping(
            value = "/cities",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> list(
            @RequestParam(value = "limit", required = false) final Integer limit,
            @RequestParam(value = "offset", required = false) final Integer offset) {

        return super.list(limit, offset);
    }

    /**
     * <p>List customers using filters and sorters.<p/>
     *
     * see {@link oshop.web.api.rest.v2.OrdersRestService#listWithFiltersAndSorters(java.util.Map, java.util.Map, Integer, Integer)}
     *
     * @param limit max results count
     * @param offset offset from the beginning of the collection
     * @return customers
     */
    @RequestMapping(
            value = "/cities/{filter}/{sort}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> listWithFiltersAndSorters(
            @MatrixVariable(pathVar="filter", required = false) final Map<String, List<String>> filters,
            @MatrixVariable(pathVar="sort", required = false) final Map<String, List<String>> sorters,
            @RequestParam(value = "limit", required = false) final Integer limit,
            @RequestParam(value = "offset", required = false) final Integer offset) {

        return super.listWithFiltersAndSorters(filters, sorters, limit, offset);
    }

    /**
     * <p>List customers using filters and sorters.<p/>
     *
     * see {@link oshop.web.api.rest.v2.OrdersRestService#listWithFiltersSortersAndProjections(java.util.Map, java.util.Map, java.util.Map, Integer, Integer)}
     *
     * @param filters same as in {@link oshop.web.api.rest.v2.OrdersRestService#listWithFiltersAndSorters(java.util.Map, java.util.Map, Integer, Integer)}
     * @param sorters same as in {@link oshop.web.api.rest.v2.OrdersRestService#listWithFiltersAndSorters(java.util.Map, java.util.Map, Integer, Integer)}
     * @param projections same as in {@link oshop.web.api.rest.v2.OrdersRestService#listWithFiltersSortersAndProjections(java.util.Map, java.util.Map, java.util.Map, Integer, Integer)}
     * @param limit max results count
     * @param offset offset from the beginning of the collection
     * @return customers
     */
    @RequestMapping(
            value = "/cities/{filter}/{sort}/{projection}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> listWithFiltersSortersAndProjections(
            @MatrixVariable(pathVar="filter", required = false) final Map<String, List<String>> filters,
            @MatrixVariable(pathVar="sort", required = false) final Map<String, List<String>> sorters,
            @MatrixVariable(pathVar="projection", required = false) final Map<String, List<String>> projections,
            @RequestParam(value = "limit", required = false) final Integer limit,
            @RequestParam(value = "offset", required = false) final Integer offset) {

        return super.listWithFiltersSortersAndProjections(filters, sorters, projections, limit, offset);
    }
}
