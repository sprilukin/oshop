package oshop.web.converter;

import org.springframework.stereotype.Component;
import oshop.model.Customer;

@Component
public class CustomerToDTOConverter extends BaseEntityConverter<Customer, Integer> {

    @Override
    protected Class<Customer> entityClass() {
        return Customer.class;
    }

    @Override
    protected void convert(Customer entity, Customer convertedEntity) throws Exception {
        convertedEntity.setName(entity.getName());
        convertedEntity.setDescription(entity.getDescription());
        convertedEntity.setImageId(entity.getImageId());
    }
}
