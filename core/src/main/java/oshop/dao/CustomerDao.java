package oshop.dao;

import org.springframework.stereotype.Repository;
import oshop.model.Customer;

@Repository
public class CustomerDao extends GenericDaoImpl<Customer, Integer> {

    @Override
    protected Class<Customer> getDomainClass() {
        return Customer.class;
    }
}
