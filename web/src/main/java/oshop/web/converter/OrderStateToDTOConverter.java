package oshop.web.converter;

import org.springframework.stereotype.Component;
import oshop.model.OrderState;
import oshop.model.Product;
import oshop.model.ProductCategory;

import javax.annotation.Resource;

@Component
public class OrderStateToDTOConverter extends BaseEntityConverter<OrderState, Integer> {

    @Override
    protected Class<OrderState> entityClass() {
        return OrderState.class;
    }

    @Override
    protected void convert(OrderState entity, OrderState convertedEntity) throws Exception {
        convertedEntity.setName(entity.getName());
        convertedEntity.setLabel(entity.getLabel());
    }
}
