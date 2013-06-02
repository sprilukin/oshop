package oshop.services.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import oshop.dao.GenericDao;
import oshop.dto.GenericListDto;
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
                "delete from order_products where order_id = :id and product_id in :ids",
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

    public GenericListDto<Product> getProductsByOrder(
            Integer orderId, Map<String, List<String>> filters, Map<String, List<String>> sorters,
            Integer limit, Integer offset) throws Exception {

        Query query = productDao.getSession().createQuery(
                "select p from oshop.model.Order o join o.products as p where o.id = :id").setInteger("id", orderId);
        List<Product> list  = productConverter.convert(productDao.list(query, limit, offset));

        Query totalQuery = productDao.getSession().createQuery(
                "select count(p.id) from oshop.model.Order o join o.products as p where o.id = :id").setInteger("id", orderId);
        Number total = (Number)totalQuery.uniqueResult();

        return new GenericListDto<Product>(list, total.longValue());
    }

    public GenericListDto<Product> getProductsByOrder(Integer orderId) throws Exception {
        Order order = getDao().get(orderId);
        List<Product> list = productConverter.convert(order.getProducts());

        return new GenericListDto<Product>(list, (long)list.size());
    }

    public OrderHasOrderStates addOrderHasStateToOrder(final Integer orderId, OrderHasOrderStates entity) throws Exception {
        Order order = getDao().get(orderId);
        entity.setOrder(order);
        Integer id = orderHasOrderStatesDao.add(entity);
        return orderHasStateConverter.convert(orderHasOrderStatesDao.get(id));
    }

    public GenericListDto<OrderHasOrderStates> getOrderHasStatesByOrder(Integer orderId) throws Exception {
        Order order = getDao().get(orderId);
        List<OrderHasOrderStates> list = orderHasStateConverter.convert(order.getStates());

        return new GenericListDto<OrderHasOrderStates>(list, (long)list.size());
    }

}
