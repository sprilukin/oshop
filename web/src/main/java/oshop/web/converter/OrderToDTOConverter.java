package oshop.web.converter;

import org.springframework.stereotype.Component;
import oshop.model.Order;
import oshop.model.OrderHasProducts;
import oshop.model.OrderHasStates;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class OrderToDTOConverter extends BaseEntityConverter<Order, Integer> {

    @Resource(name = "orderHasProductsToDTOConverter")
    private EntityConverter<OrderHasProducts, Integer> orderProductsConverter;

    @Resource(name = "orderHasStateToDTOConverter")
    private EntityConverter<OrderHasStates, Integer> orderStatesConverter;

    protected void convertInternal(Order entity, Order convertedEntity) throws Exception {
        convertedEntity.setCustomer(null); //TODO
        convertedEntity.setDiscount(null); //TODO
        convertedEntity.setPrepayment(null); //TODO
        convertedEntity.setShippingAddress(null); //TODO
    }

    @Override
    protected Class<Order> entityClass() {
        return Order.class;
    }

    @Override
    protected void convert(Order entity, Order convertedEntity) throws Exception {
        convertInternal(entity, convertedEntity);

        convertedEntity.setProducts(orderProductsConverter.convert(entity.getProducts()));
        convertedEntity.setStates(orderStatesConverter.convert(entity.getStates()));
    }

    protected Order convertForList(Order entity) throws Exception {
        Order convertedEntity = newInstance();
        convertInternal(entity, convertedEntity);

        List<OrderHasStates> states = entity.getStates();
        if (states.size() > 0) {
            convertedEntity.setStates(Arrays.asList(states.get(0)));
        } else {
            convertedEntity.setStates(Collections.<OrderHasStates>emptyList());
        }

        return convertedEntity;
    }
}
