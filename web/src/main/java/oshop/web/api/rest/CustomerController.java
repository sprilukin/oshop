package oshop.web.api.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import oshop.dao.GenericDao;
import oshop.model.Customer;
import oshop.model.Order;
import oshop.model.ShippingAddress;
import oshop.web.api.rest.adapter.EntityListDetachingRestCallbackAdapter;
import oshop.web.api.rest.filter.Filter;
import oshop.web.converter.EntityConverter;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/customers")
@Transactional(readOnly = true)
public class CustomerController extends BaseController<Customer, Integer> {

    private static final Log log = LogFactory.getLog(CustomerController.class);

    @Resource
    private GenericDao<Customer, Integer> customerDao;

    @Resource
    private GenericDao<ShippingAddress, Integer> shippingAddressDao;

    @Resource
    private GenericDao<Order, Integer> orderDao;

    @Resource
    private Filter ordersFilter;

    @Resource(name = "customerToDTOConverter")
    private EntityConverter<Customer, Integer> converter;

    @Resource(name = "shippingAddressToDTOConverter")
    private EntityConverter<ShippingAddress, Integer> shippingAddressConverter;

    @Resource(name = "orderToDTOConverter")
    private EntityConverter<Order, Integer> orderConverter;

    @Override
    protected GenericDao<Customer, Integer> getDao() {
        return customerDao;
    }

    @Override
    protected EntityConverter<Customer, Integer> getToDTOConverter() {
        return converter;
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

        return new EntityListDetachingRestCallbackAdapter<ShippingAddress, Integer>(shippingAddressConverter, getSearchDao()) {

            @Override
            protected Criteria getCriteria() {
                Criteria criteria = shippingAddressDao.createCriteria();
                criteria.createAlias("customer", "c").add(Restrictions.eq("c.id", id));
                getFilter().applyFilters(filters, criteria);
                getSorter().applySorters(sorters, criteria);

                return criteria;
            }

            @Override
            protected List<ShippingAddress> getList(Criteria criteria) {
                return shippingAddressDao.list(criteria, offset, limit);
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

        return new EntityListDetachingRestCallbackAdapter<Order, Integer>(orderConverter, getSearchDao()) {

            @Override
            protected Criteria getCriteria() {
                Criteria criteria = orderDao.createCriteria();
                criteria.createAlias("customer", "c").add(Restrictions.eq("c.id", id));
                ordersFilter.applyFilters(filters, criteria);
                getSorter().applySorters(sorters, criteria);

                return criteria;
            }

            @Override
            protected List<Order> getList(Criteria criteria) {
                return orderDao.list(criteria, offset, limit);
            }
        }.invoke();
    }

}
