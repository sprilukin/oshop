package oshop.services.converter;

import org.springframework.stereotype.Component;
import oshop.model.City;
import oshop.model.Customer;
import oshop.model.ShippingAddress;
import oshop.model.ShippingType;

import javax.annotation.Resource;

@Component
public class ShippingAddressToDTOConverter extends BaseEntityConverter<ShippingAddress, Integer> {

    @Resource(name = "customerToDTOConverter")
    private EntityConverter<Customer, Integer> customerConverter;

    @Resource(name = "shippingTypeToDTOConverter")
    private EntityConverter<ShippingType, Integer> shippingTypeConverter;

    @Resource(name = "cityToDTOConverter")
    private EntityConverter<City, Integer> cityConverter;

    @Override
    protected Class<ShippingAddress> entityClass() {
        return ShippingAddress.class;
    }

    @Override
    protected void convert(ShippingAddress entity, ShippingAddress convertedEntity) throws Exception {
        convertedEntity.setCustomer(customerConverter.convert(entity.getCustomer()));
        convertedEntity.setShippingType(shippingTypeConverter.convert(entity.getShippingType()));
        convertedEntity.setAddress(entity.getAddress());
        convertedEntity.setCity(cityConverter.convert(entity.getCity()));
        convertedEntity.setPhone(entity.getPhone());
        convertedEntity.setRecipient(entity.getRecipient());
        convertedEntity.setPostalCode(entity.getPostalCode());
    }
}
