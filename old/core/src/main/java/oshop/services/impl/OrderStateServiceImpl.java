package oshop.services.impl;

import org.springframework.stereotype.Service;
import oshop.dao.GenericDao;
import oshop.model.OrderState;
import oshop.services.converter.EntityConverter;

import javax.annotation.Resource;

@Service("orderStateService")
public class OrderStateServiceImpl extends GenericServiceImpl<OrderState, Integer> {

    @Resource
    protected GenericDao<OrderState, Integer> orderStateDao;

    @Resource(name = "orderStateToDTOConverter")
    private EntityConverter<OrderState, Integer> converter;

    @Override
    protected GenericDao<OrderState, Integer> getDao() {
        return orderStateDao;
    }

    @Override
    protected EntityConverter<OrderState, Integer> getToDTOConverter() {
        return converter;
    }
}
