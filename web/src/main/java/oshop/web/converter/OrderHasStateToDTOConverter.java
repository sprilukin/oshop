package oshop.web.converter;

import org.springframework.stereotype.Component;
import oshop.model.OrderHasStates;
import oshop.model.OrderState;

import javax.annotation.Resource;

@Component
public class OrderHasStateToDTOConverter extends BaseEntityConverter<OrderHasStates, Integer> {

    @Resource(name = "orderStateToDTOConverter")
    private EntityConverter<OrderState, Integer> orderStateConverter;

    @Override
    protected Class<OrderHasStates> entityClass() {
        return OrderHasStates.class;
    }

    @Override
    protected void convert(OrderHasStates entity, OrderHasStates convertedEntity) throws Exception {
        convertedEntity.setComment(entity.getComment());
        convertedEntity.setDate(entity.getDate());
        convertedEntity.setOrderState(orderStateConverter.convert(entity.getOrderState()));
    }
}
