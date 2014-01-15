package oshop.services;

import org.springframework.transaction.annotation.Transactional;
import oshop.dto.PaginatedList;
import oshop.model.Customer;
import oshop.model.Order;
import oshop.model.ShippingAddress;

import java.util.List;
import java.util.Map;

@Transactional(readOnly = true)
public interface CustomerService extends GenericService<Customer, Integer> {

    public PaginatedList<ShippingAddress> getShippingAddressesByCustomer(
            Integer customerId, Map<String, List<String>> filters, Map<String, List<String>> sorters,
            Integer limit, Integer offset)
                throws Exception;

    public PaginatedList<Order> getOrdersByCustomer(
            Integer customerId, Map<String, List<String>> filters, Map<String, List<String>> sorters,
            Integer limit, Integer offset)
                throws Exception;
}
