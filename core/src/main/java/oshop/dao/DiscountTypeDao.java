package oshop.dao;

import org.springframework.stereotype.Repository;
import oshop.model.Customer;
import oshop.model.DiscountType;

@Repository
public class DiscountTypeDao /*extends GenericDao<DiscountType, Integer> */{

    //@Override
    protected Class<DiscountType> getDomainClass() {
        return DiscountType.class;
    }
}
