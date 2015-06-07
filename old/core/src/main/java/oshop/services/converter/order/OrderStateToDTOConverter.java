package oshop.services.converter.order;

import org.springframework.stereotype.Component;
import oshop.model.OrderState;
import oshop.services.converter.BaseEntityConverter;

@Component
public class OrderStateToDTOConverter extends BaseEntityConverter<OrderState, Integer> {

    @Override
    protected Class<OrderState> entityClass() {
        return OrderState.class;
    }

    @Override
    protected void convert(OrderState entity, OrderState convertedEntity) throws Exception {
        convertedEntity.setName(entity.getName());
    }
}
