package oshop.services.converter;

import org.springframework.stereotype.Component;
import oshop.model.Product;
import oshop.model.ProductCategory;

import javax.annotation.Resource;

@Component
public class ProductToDTOConverter extends BaseEntityConverter<Product, Integer> {

    @Override
    protected Class<Product> entityClass() {
        return Product.class;
    }

    @Resource(name = "productCategoryToDTOConverter")
    private EntityConverter<ProductCategory, Integer> productCategoryConvetrer;

    @Override
    protected void convert(Product entity, Product convertedEntity) throws Exception {
        convertedEntity.setName(entity.getName());
        convertedEntity.setDescription(entity.getDescription());
        convertedEntity.setImageId(entity.getImageId());
        convertedEntity.setPrice(entity.getPrice());
        convertedEntity.setCategory(productCategoryConvetrer.convert(entity.getCategory()));
    }
}
