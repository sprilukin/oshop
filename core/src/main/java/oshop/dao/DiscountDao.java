package oshop.dao;

import org.springframework.stereotype.Repository;
import oshop.model.Customer;
import oshop.model.Discount;

@Repository
public class DiscountDao /*extends GenericDao<Discount, Integer>*/ {

    //@Override
    protected Class<Discount> getDomainClass() {
        return Discount.class;
    }
}
