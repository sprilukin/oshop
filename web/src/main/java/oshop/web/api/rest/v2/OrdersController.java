package oshop.web.api.rest.v2;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import oshop.model.Order;
import oshop.services.GenericService;
import oshop.services.OrderService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/v2")
public class OrdersController extends BaseController<Order, Integer> {

    private static final Log log = LogFactory.getLog(OrdersController.class);

    @Resource
    protected OrderService orderService;

    @Override
    protected GenericService<Order, Integer> getService() {
        return orderService;
    }

    /* ===== Model ===== */


    /**
     * Delete order
     *
     * @param id the id if the order to remove
     * @return nothing
     */
    @RequestMapping(
            value = "/orders/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable final Integer id) {
        return super.delete(id);
    }

    /**
     * Get order by id
     *
     * @param id the id if the order
     * @return order
     */
    @RequestMapping(
            value = "/orders/{id}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable final Integer id) {
        return super.get(id);
    }

    /**
     * Update order with given id
     *
     * @param id the id if the order
     * @return order with updates
     */
    @RequestMapping(
            value = "/orders/{id}",
            method = RequestMethod.PUT,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> update(
            @PathVariable final Integer id,
            @RequestBody @Valid final Order entity, final BindingResult result) {

        return super.update(id, entity, result);
    }

    /* ===== Collection ===== */

    /**
     * Add new order
     *
     * @param entity order
     * @param result binding result
     * @return
     */
    @RequestMapping(
            value = "/orders",
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> add(@RequestBody @Valid final Order entity, final BindingResult result) {
        return super.add(entity, result);
    }

    /**
     * List orders
     *
     * @param limit max results count
     * @param offset offset from the beginning of the collection
     * @return orders
     */
    @RequestMapping(
            value = "/orders",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> list(
            @RequestParam(value = "limit", required = false) final Integer limit,
            @RequestParam(value = "offset", required = false) final Integer offset) {

        return super.list(limit, offset);
    }

    /**
     * <p>List orders
     * using filters and sorters.<p/>
     *
     * <p>Example:</p>
     *
     * <code>
     *     /orders/filter;orderStateNotIn=Sent;customerLIKE=Customer;/sort;id=desc;?limit=50&offset=0
     * </code>
     *
     * @param filters filters:
     *                <code>filter;{filterName1}={filterValue1};...;{filterNameN}={filterValueN};</code>
     *                or
     *                <code>filter;;</code>
     * @param sorters sorters:
     *                <code>sorter;{field1}={asc|desc};...;{fieldN}={asc|desc};</code>
     *                or
     *                <code>sorter;;</code>
     * @param limit max results count
     * @param offset offset from the beginning of the collection
     * @return orders
     */
    @RequestMapping(
            value = "/orders/{filter}/{sort}",
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
     * <p>List orders
     * using filters and sorters.<p/>
     *
     * <p>Example:</p>
     *
     * <code>
     *     /orders/filter;orderStateNotIn=Sent;customerLIKE=Customer;/sort;id=desc;/projection;date=GROUP;amount=SUM?limit=50&offset=0
     * </code>
     *
     * @param filters same as in {@link #listWithFiltersAndSorters(java.util.Map, java.util.Map, Integer, Integer)}
     * @param sorters same as in {@link #listWithFiltersAndSorters(java.util.Map, java.util.Map, Integer, Integer)}
     * @param projections projections:
     *                <code>projection;{field1}={projectionType1};...;{fieldN}={projectionTypeN};</code>
     *                or
     *                <code>projection;;</code>
     * @param limit max results count
     * @param offset offset from the beginning of the collection
     * @return orders
     */
    @RequestMapping(
            value = "/orders/{filter}/{sort}/{projection}",
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
