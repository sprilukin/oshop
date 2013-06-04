package oshop.services.impl;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Service;
import oshop.dao.GenericDao;
import oshop.dto.ListWithTotalSize;
import oshop.dto.PaginatedCollectionList;
import oshop.model.Order;
import oshop.model.OrderHasOrderStates;
import oshop.model.Product;
import oshop.services.OrderService;
import oshop.services.filter.Filter;
import oshop.services.converter.EntityConverter;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("orderService")
public class OrderServiceImpl extends GenericServiceImpl<Order, Integer> implements OrderService {

    public static final String DELETE_PRODUCTS_FROM_ORDER_SQL =
            "delete from order_products where order_id = :id and product_id in :ids";

    public static final String ALL_PRODUCTS_FROM_ORDER_EXCEPT_THIS_SQL =
            "not ({alias}.id in (select p.id from orders o " +
            "inner join order_products op on o.id=op.order_id " +
            "inner join product p on op.product_id=p.id where o.id = ? ))";

    @Resource
    protected GenericDao<Order, Integer> orderDao;

    @Resource(name = "orderToDTOConverter")
    private EntityConverter<Order, Integer> converter;

    @Resource
    private GenericDao<Product, Integer> productDao;

    @Resource
    private GenericDao<OrderHasOrderStates, Integer> orderHasOrderStatesDao;

    @Resource
    private Filter ordersFilter;

    @Resource
    private Filter productsFilter;

    @Resource(name = "orderFromDTOConverter")
    private EntityConverter<Order, Integer> fromDtoConverter;

    @Resource(name = "orderHasStateToDTOConverter")
    private EntityConverter<OrderHasOrderStates, Integer> orderHasStateConverter;

    @Resource(name = "productToDTOConverter")
    private EntityConverter<Product, Integer> productConverter;

    @Override
    protected GenericDao<Order, Integer> getDao() {
        return orderDao;
    }

    @Override
    protected EntityConverter<Order, Integer> getToDTOConverter() {
        return converter;
    }

    @Override
    protected EntityConverter<Order, Integer> getFromDTOConverter() {
        return fromDtoConverter;
    }

    @Override
    protected Filter getFilter() {
        return ordersFilter;
    }

    public void deleteProductsFromOrder(final Integer orderId, final List<Integer> ids) throws Exception {
        getDao().executeQuery(
                DELETE_PRODUCTS_FROM_ORDER_SQL,
                new GenericDao.SQLQueryManipulator() {
                    @Override
                    public void manipulateWithQuery(SQLQuery query) {
                        query.setParameterList("ids", ids).setParameter("id", orderId);
                    }
                });

        getDao().getSession().flush();
    }

    public void addProductsToOrder(final Integer orderId, final List<Integer> ids) {
        Order order = getDao().get(orderId);

        Criteria productsCriteria = productDao.createCriteria();
        productsCriteria.add(Restrictions.in("id", ids));
        List<Product> products = productDao.list(productsCriteria);


        order.getProducts().addAll(products);
        getDao().update(order);
    }

    public PaginatedCollectionList<Product> getProductsAllButOrder(
            Integer orderId, Map<String, List<String>> filters, Map<String, List<String>> sorters,
            Integer limit, Integer offset) throws Exception {

        Criteria criteria = productDao.createCriteria();
        criteria.add(Restrictions.sqlRestriction(
                ALL_PRODUCTS_FROM_ORDER_EXCEPT_THIS_SQL,
                orderId, StandardBasicTypes.INTEGER));

        productsFilter.applyFilters(filters, criteria);
        getSorter().applySorters(sorters, criteria);

        List<Product> list = productConverter.convert(productDao.list(criteria, offset, limit));

        return getCountAndPrepareListDto(list, criteria);
    }

    public PaginatedCollectionList<Product> getProductsByOrder(Integer orderId) throws Exception {
        Order order = getDao().get(orderId);
        List<Product> list = productConverter.convert(order.getProducts());

        return new ListWithTotalSize<Product>(list, (long)list.size());
    }

    public OrderHasOrderStates addOrderHasStateToOrder(final Integer orderId, OrderHasOrderStates entity) throws Exception {
        Order order = getDao().get(orderId);
        entity.setOrder(order);
        Integer id = orderHasOrderStatesDao.add(entity);
        return orderHasStateConverter.convert(orderHasOrderStatesDao.get(id));
    }

    public PaginatedCollectionList<OrderHasOrderStates> getOrderHasStatesByOrder(Integer orderId) throws Exception {
        Order order = getDao().get(orderId);
        List<OrderHasOrderStates> list = orderHasStateConverter.convert(order.getStates());

        return new ListWithTotalSize<OrderHasOrderStates>(list, (long)list.size());
    }

}
