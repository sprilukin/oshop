package oshop.services.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import oshop.dao.GenericDao;
import oshop.dto.PaginatedCollectionList;
import oshop.model.Customer;
import oshop.model.Order;
import oshop.model.ShippingAddress;
import oshop.services.CustomerService;
import oshop.services.filter.Filter;
import oshop.services.converter.EntityConverter;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("customerService")
public class CustomerServiceImpl extends GenericServiceImpl<Customer, Integer> implements CustomerService {

    @Resource
    protected GenericDao<Customer, Integer> customerDao;

    @Resource(name = "customerToDTOConverter")
    private EntityConverter<Customer, Integer> converter;

    @Resource
    private GenericDao<ShippingAddress, Integer> shippingAddressDao;

    @Resource
    private GenericDao<Order, Integer> orderDao;

    @Resource
    private Filter ordersFilter;

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

    public PaginatedCollectionList<ShippingAddress> getShippingAddressesByCustomer(Integer customerId,
            Map<String, List<String>> filters, Map<String, List<String>> sorters, Integer limit, Integer offset) throws Exception {

        Criteria criteria = shippingAddressDao.createCriteria();
        criteria.createAlias("customer", "c").add(Restrictions.eq("c.id", customerId));
        criteria = applyFiltersAndSortersToCriteria(criteria, filters, sorters);

        List<ShippingAddress> list = shippingAddressConverter.convert(shippingAddressDao.list(criteria, offset, limit));

        return getCountAndPrepareListDto(list, criteria);
    }

    public PaginatedCollectionList<Order> getOrdersByCustomer(Integer customerId,
            Map<String, List<String>> filters, Map<String, List<String>> sorters, Integer limit, Integer offset) throws Exception {

        Criteria criteria = orderDao.createCriteria();
        criteria.createAlias("customer", "c").add(Restrictions.eq("c.id", customerId));
        ordersFilter.applyFilters(filters, criteria);
        getSorter().applySorters(sorters, criteria);

        List<Order> list = orderConverter.convert(orderDao.list(criteria, offset, limit));

        return getCountAndPrepareListDto(list, criteria);
    }
}
