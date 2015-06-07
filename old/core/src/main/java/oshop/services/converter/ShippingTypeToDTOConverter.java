package oshop.services.converter;

import org.springframework.stereotype.Component;
import oshop.model.ShippingType;

@Component
public class ShippingTypeToDTOConverter extends BaseEntityConverter<ShippingType, Integer> {

    @Override
    protected Class<ShippingType> entityClass() {
        return ShippingType.class;
    }

    @Override
    protected void convert(ShippingType entity, ShippingType convertedEntity) throws Exception {
        convertedEntity.setName(entity.getName());
    }
}
