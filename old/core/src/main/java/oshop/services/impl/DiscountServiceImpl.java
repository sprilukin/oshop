package oshop.services.impl;

import org.springframework.stereotype.Service;
import oshop.dao.GenericDao;
import oshop.model.Discount;
import oshop.services.converter.EntityConverter;

import javax.annotation.Resource;

@Service("discountService")
public class DiscountServiceImpl extends GenericServiceImpl<Discount, Integer> {

    @Resource
    protected GenericDao<Discount, Integer> discountDao;

    @Resource(name = "discountToDTOConverter")
    private EntityConverter<Discount, Integer> converter;

    @Override
    protected GenericDao<Discount, Integer> getDao() {
        return discountDao;
    }

    @Override
    protected EntityConverter<Discount, Integer> getToDTOConverter() {
        return converter;
    }
}
