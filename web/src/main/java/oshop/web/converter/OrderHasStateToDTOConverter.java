package oshop.web.converter;

import org.springframework.stereotype.Component;
import oshop.model.OrderHasOrderStates;
import oshop.model.OrderState;

import javax.annotation.Resource;

@Component
public class OrderHasStateToDTOConverter extends BaseEntityConverter<OrderHasOrderStates, Integer> {

    @Resource(name = "orderStateToDTOConverter")
    private EntityConverter<OrderState, Integer> orderStateConverter;

    @Override
    protected Class<OrderHasOrderStates> entityClass() {
        return OrderHasOrderStates.class;
    }

    @Override
    protected void convert(OrderHasOrderStates entity, OrderHasOrderStates convertedEntity) throws Exception {
        convertedEntity.setDescription(entity.getDescription());
        convertedEntity.setDate(entity.getDate());
        convertedEntity.setOrderState(orderStateConverter.convert(entity.getOrderState()));
    }
}
