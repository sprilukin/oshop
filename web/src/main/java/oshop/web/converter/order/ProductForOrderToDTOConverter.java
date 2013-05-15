package oshop.web.converter.order;

import org.springframework.stereotype.Component;
import oshop.model.Product;
import oshop.web.converter.BaseEntityConverter;

@Component
public class ProductForOrderToDTOConverter extends BaseEntityConverter<Product, Integer> {

    @Override
    protected Class<Product> entityClass() {
        return Product.class;
    }

    @Override
    protected void convert(Product entity, Product convertedEntity) throws Exception {
        convertedEntity.setName(entity.getName());
        convertedEntity.setDescription(entity.getDescription());
        convertedEntity.setImageId(entity.getImageId());
        convertedEntity.setPrice(entity.getPrice());
    }
}
