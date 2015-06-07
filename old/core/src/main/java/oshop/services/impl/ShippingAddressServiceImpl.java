package oshop.services.impl;

import org.springframework.stereotype.Service;
import oshop.dao.GenericDao;
import oshop.model.ShippingAddress;
import oshop.services.converter.EntityConverter;

import javax.annotation.Resource;

@Service("shippingAddressService")
public class ShippingAddressServiceImpl extends GenericServiceImpl<ShippingAddress, Integer> {

    @Resource
    protected GenericDao<ShippingAddress, Integer> shippingAddressDao;

    @Resource(name = "shippingAddressToDTOConverter")
    private EntityConverter<ShippingAddress, Integer> converter;

    @Override
    protected GenericDao<ShippingAddress, Integer> getDao() {
        return shippingAddressDao;
    }

    @Override
    protected EntityConverter<ShippingAddress, Integer> getToDTOConverter() {
        return converter;
    }
}
