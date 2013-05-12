package oshop.web.converter;

import org.springframework.stereotype.Component;
import oshop.model.Order;
import oshop.model.OrderHasOrderStates;
import oshop.model.Product;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class OrderToDTOConverter extends BaseEntityConverter<Order, Integer> {

    @Resource(name = "productToDTOConverter")
    private EntityConverter<Product, Integer> productsConverter;

    @Resource(name = "orderHasStateToDTOConverter")
    private EntityConverter<OrderHasOrderStates, Integer> orderStatesConverter;

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

        convertedEntity.setProducts(productsConverter.convert(entity.getProducts()));
        convertedEntity.setStates(orderStatesConverter.convert(entity.getStates()));
    }

    protected Order convertForCollection(Order entity) throws Exception {
        Order convertedEntity = newInstance();
        convertInternal(entity, convertedEntity);

        List<OrderHasOrderStates> states = entity.getStates();
        if (states.size() > 0) {
            convertedEntity.setStates(Arrays.asList(states.get(0)));
        } else {
            convertedEntity.setStates(Collections.<OrderHasOrderStates>emptyList());
        }

        return convertedEntity;
    }
}
