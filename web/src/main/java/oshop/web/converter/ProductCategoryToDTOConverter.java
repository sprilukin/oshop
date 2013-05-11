package oshop.web.converter;

import org.springframework.stereotype.Component;
import oshop.model.Product;
import oshop.model.ProductCategory;

import javax.annotation.Resource;

@Component
public class ProductCategoryToDTOConverter extends BaseEntityConverter<ProductCategory, Integer> {

    @Override
    protected Class<ProductCategory> entityClass() {
        return ProductCategory.class;
    }

    @Override
    protected void convert(ProductCategory entity, ProductCategory convertedEntity) throws Exception {
        convertedEntity.setName(entity.getName());
    }
}
