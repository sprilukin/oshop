package oshop.services;

import org.springframework.transaction.annotation.Transactional;
import oshop.dto.PaginatedCollectionList;
import oshop.model.Order;
import oshop.model.OrderHasOrderStates;
import oshop.model.Product;

import java.util.List;
import java.util.Map;

@Transactional(readOnly = true)
public interface OrderService extends GenericService<Order, Integer> {

    @Transactional(readOnly = false)
    public void deleteProductsFromOrder(final Integer orderId, final List<Integer> ids) throws Exception;

    @Transactional(readOnly = false)
    public void addProductsToOrder(final Integer orderId, final List<Integer> ids);

    public PaginatedCollectionList<Product> getProductsByOrder(
            Integer orderId, Map<String, List<String>> filters, Map<String, List<String>> sorters,
            Integer limit, Integer offset) throws Exception;

    public PaginatedCollectionList<Product> getProductsByOrder(Integer orderId) throws Exception;

    @Transactional(readOnly = false)
    public OrderHasOrderStates addOrderHasStateToOrder(final Integer orderId, OrderHasOrderStates entity) throws Exception;

    public PaginatedCollectionList<OrderHasOrderStates> getOrderHasStatesByOrder(Integer orderId) throws Exception;
}
