package oshop.services.impl;

import org.springframework.stereotype.Service;
import oshop.dao.GenericDao;
import oshop.model.OrderHasOrderStates;
import oshop.services.converter.EntityConverter;

import javax.annotation.Resource;

@Service("orderHasOrderStatesService")
public class OrderHasOrderStatesServiceImpl extends GenericServiceImpl<OrderHasOrderStates, Integer> {

    @Resource
    protected GenericDao<OrderHasOrderStates, Integer> orderHasOrderStatesDao;

    @Resource(name = "orderHasStateToDTOConverter")
    private EntityConverter<OrderHasOrderStates, Integer> converter;

    @Override
    protected GenericDao<OrderHasOrderStates, Integer> getDao() {
        return orderHasOrderStatesDao;
    }

    @Override
    protected EntityConverter<OrderHasOrderStates, Integer> getToDTOConverter() {
        return converter;
    }
}
