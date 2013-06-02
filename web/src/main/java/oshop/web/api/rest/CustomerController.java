package oshop.web.api.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import oshop.model.Customer;
import oshop.model.Order;
import oshop.model.ShippingAddress;
import oshop.services.CustomerService;
import oshop.services.impl.CustomerServiceImpl;
import oshop.services.GenericService;
import oshop.web.api.rest.adapter.ListReturningRestCallbackAdapter;
import oshop.dto.GenericListDto;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/customers")
public class CustomerController extends BaseController<Customer, Integer> {

    private static final Log log = LogFactory.getLog(CustomerController.class);

    @Resource
    protected CustomerService customerService;

    @Override
    protected GenericService<Customer, Integer> getService() {
        return customerService;
    }

    @RequestMapping(
            value = "/{id}/shippingAddresses",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> shippingAddresses(
            @PathVariable final Integer id,
            @RequestParam(value = "limit", required = false) final Integer limit,
            @RequestParam(value = "offset", required = false) final Integer offset) {

        return shippingAddressesWithFiltersAndSorters(id,
                Collections.<String, List<String>>emptyMap(),
                Collections.<String, List<String>>emptyMap(), limit, offset);
    }

    @RequestMapping(
            value = "/{id}/shippingAddresses/{filter}/{sort}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> shippingAddressesWithFiltersAndSorters(
            @PathVariable final Integer id,
            @MatrixVariable(pathVar="filter", required = false) final Map<String, List<String>> filters,
            @MatrixVariable(pathVar="sort", required = false) final Map<String, List<String>> sorters,
            @RequestParam(value = "limit", required = false) final Integer limit,
            @RequestParam(value = "offset", required = false) final Integer offset) {

        return new ListReturningRestCallbackAdapter<ShippingAddress>() {

            @Override
            protected GenericListDto<ShippingAddress> getListDto() throws Exception {
                return customerService.getShippingAddressesByCustomer(id, filters, sorters, limit, offset);
            }
        }.invoke();
    }

    @RequestMapping(
            value = "/{id}/orders",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> orders(
            @PathVariable final Integer id,
            @RequestParam(value = "limit", required = false) final Integer limit,
            @RequestParam(value = "offset", required = false) final Integer offset) {

        return ordersWithFiltersAndSorters(id,
                Collections.<String, List<String>>emptyMap(),
                Collections.<String, List<String>>emptyMap(), limit, offset);
    }

    @RequestMapping(
            value = "/{id}/orders/{filter}/{sort}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> ordersWithFiltersAndSorters(
            @PathVariable final Integer id,
            @MatrixVariable(pathVar="filter", required = false) final Map<String, List<String>> filters,
            @MatrixVariable(pathVar="sort", required = false) final Map<String, List<String>> sorters,
            @RequestParam(value = "limit", required = false) final Integer limit,
            @RequestParam(value = "offset", required = false) final Integer offset) {

        return new ListReturningRestCallbackAdapter<Order>() {

            @Override
            protected GenericListDto<Order> getListDto() throws Exception {
                return customerService.getOrdersByCustomer(id, filters, sorters, limit, offset);
            }
        }.invoke();
    }

}
