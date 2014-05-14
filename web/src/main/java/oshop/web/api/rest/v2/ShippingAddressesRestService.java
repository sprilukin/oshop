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
import oshop.model.ShippingAddress;
import oshop.services.GenericService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/v2")
public class ShippingAddressesRestService extends BaseRestService<ShippingAddress, Integer> {

    @Resource
    private GenericService<ShippingAddress, Integer> productService;

    @Override
    protected GenericService<ShippingAddress, Integer> getService() {
        return productService;
    }

    /* ===== Model ===== */

    /**
     * Delete shippingAddress
     *
     * @param id the id of the shippingAddress to remove
     * @return nothing
     */
    @RequestMapping(
            value = "/shippingAddresses/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable final Integer id) {
        return super.delete(id);
    }

    /**
     * Get shippingAddress by id
     *
     * @param id the id of the shippingAddress
     * @return shippingAddress
     */
    @RequestMapping(
            value = "/shippingAddresses/{id}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable final Integer id) {
        return super.get(id);
    }

    /**
     * Update shippingAddress with given id
     *
     * @param id the id of the shippingAddress
     * @return shippingAddress with updates
     */
    @RequestMapping(
            value = "/shippingAddresses/{id}",
            method = RequestMethod.PUT,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> update(
            @PathVariable final Integer id,
            @RequestBody @Valid final ShippingAddress entity, final BindingResult result) {

        return super.update(id, entity, result);
    }

    /* ===== Collection ===== */

    /**
     * Add new shippingAddress
     *
     * @param entity shippingAddress
     * @param result binding result
     * @return newly created shippingAddress
     */
    @RequestMapping(
            value = "/shippingAddresses",
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> add(@RequestBody @Valid final ShippingAddress entity, final BindingResult result) {
        return super.add(entity, result);
    }

    /**
     * List shippingAddresses
     *
     * @param limit max results count
     * @param offset offset from the beginning of the collection
     * @return shippingAddresses
     */
    @RequestMapping(
            value = "/shippingAddresses",
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
     * see {@link OrdersRestService#listWithFiltersAndSorters(java.util.Map, java.util.Map, Integer, Integer)}
     *
     * @param limit max results count
     * @param offset offset from the beginning of the collection
     * @return customers
     */
    @RequestMapping(
            value = "/shippingAddresses/{filter}/{sort}",
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
     * see {@link OrdersRestService#listWithFiltersSortersAndProjections(java.util.Map, java.util.Map, java.util.Map, Integer, Integer)}
     *
     * @param filters same as in {@link OrdersRestService#listWithFiltersAndSorters(java.util.Map, java.util.Map, Integer, Integer)}
     * @param sorters same as in {@link OrdersRestService#listWithFiltersAndSorters(java.util.Map, java.util.Map, Integer, Integer)}
     * @param projections same as in {@link OrdersRestService#listWithFiltersSortersAndProjections(java.util.Map, java.util.Map, java.util.Map, Integer, Integer)}
     * @param limit max results count
     * @param offset offset from the beginning of the collection
     * @return customers
     */
    @RequestMapping(
            value = "/shippingAddresses/{filter}/{sort}/{projection}",
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
