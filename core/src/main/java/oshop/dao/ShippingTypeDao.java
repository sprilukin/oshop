package oshop.dao;

import org.springframework.stereotype.Repository;
import oshop.model.Customer;

@Repository
public class ShippingTypeDao extends GenericDao<Customer, Integer> {

    @Override
    protected Class<Customer> getDomainClass() {
        return Customer.class;
    }
}
