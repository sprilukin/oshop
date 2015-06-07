package oshop.services.impl;

import org.springframework.stereotype.Service;
import oshop.dao.GenericDao;
import oshop.model.ShippingType;
import oshop.services.converter.EntityConverter;

import javax.annotation.Resource;

@Service("shippingTypeService")
public class ShippingTypeServiceImpl extends GenericServiceImpl<ShippingType, Integer> {

    @Resource
    protected GenericDao<ShippingType, Integer> shippingTypeDao;

    @Resource(name = "shippingTypeToDTOConverter")
    private EntityConverter<ShippingType, Integer> converter;

    @Override
    protected GenericDao<ShippingType, Integer> getDao() {
        return shippingTypeDao;
    }

    @Override
    protected EntityConverter<ShippingType, Integer> getToDTOConverter() {
        return converter;
    }
}
