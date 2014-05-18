package oshop.services.impl.v2;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import oshop.dao.GenericDao;
import oshop.model.Order;
import oshop.model.OrderHasOrderStates;
import oshop.model.Product;
import oshop.services.converter.EntityConverter;
import oshop.services.filter.Filter;
import oshop.services.impl.GenericServiceImpl;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("orderServiceV2")
public class OrderServiceV2Impl extends GenericServiceImpl<Order, Integer> {

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

    @Override
    public Order add(Order entity) throws Exception {
        entity.setStates(mergePersistentAndNewOrderHasOrderStatesByDtos(entity, entity.getStates()));

        Order order = getFromDTOConverter().convert(entity);
        Integer id = getDao().add(order);
        order = getDao().get(id);

        order.setProducts(getPersistentProductsByDtos(entity.getProducts()));
        if (!order.getProducts().isEmpty()) {
            getDao().update(order);
        }

        getDao().getSession().clear();
        return getToDTOConverter().convert(getDao().get(id));
    }

    @Override
    public Order update(Order entity, Integer id) throws Exception {
        entity.setId(id);
        Order order = getFromDTOConverter().convert(entity);

        List<Product> updatedProducts = getPersistentProductsByDtos(entity.getProducts());
        order.setProducts(updatedProducts);
        order.getStates().clear();
        order.getStates().addAll(mergePersistentAndNewOrderHasOrderStatesByDtos(order, entity.getStates()));

        getDao().update(order);
        getDao().getSession().clear();
        return getToDTOConverter().convert(getDao().get(id));
    }

    private List<Product> getPersistentProductsByDtos(List<Product> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return dtos;
        }

        Criteria productsCriteria = productDao.createCriteria();
        productsCriteria.add(Restrictions.in("id",
                CollectionUtils.collect(dtos,
                        new Transformer<Product, Integer>() {
                            @Override
                            public Integer transform(Product input) {
                                return input.getId();
                            }})));

        return productDao.list(productsCriteria);
    }

    private List<OrderHasOrderStates> mergePersistentAndNewOrderHasOrderStatesByDtos(Order order, List<OrderHasOrderStates> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return null;
        }

        List<Integer> existingIds = new ArrayList<Integer>();
        List<OrderHasOrderStates> newStates = new ArrayList<OrderHasOrderStates>();
        for (OrderHasOrderStates state: dtos) {
            if (state.getId() == null) {
                state.setOrder(order);
                newStates.add(state);
            } else {
                existingIds.add(state.getId());
            }
        }

        List<OrderHasOrderStates> states = newStates;

        if (existingIds.size() > 0) {
            Criteria statesCriteria = orderHasOrderStatesDao.createCriteria();
            statesCriteria.add(Restrictions.in("id", existingIds));


            states = orderHasOrderStatesDao.list(statesCriteria);
            states.addAll(newStates);
        }

        return states;
    }
}
