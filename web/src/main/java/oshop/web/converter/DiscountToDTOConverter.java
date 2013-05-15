package oshop.web.converter;

import org.springframework.stereotype.Component;
import oshop.model.Discount;

@Component
public class DiscountToDTOConverter extends BaseEntityConverter<Discount, Integer> {

    @Override
    protected Class<Discount> entityClass() {
        return Discount.class;
    }

    @Override
    protected void convert(Discount entity, Discount convertedEntity) throws Exception {
        convertedEntity.setDescription(entity.getDescription());
        convertedEntity.setAmount(entity.getAmount());
        convertedEntity.setType(entity.getType());
        convertedEntity.setTypeAsString(entity.getTypeAsString());
    }
}
