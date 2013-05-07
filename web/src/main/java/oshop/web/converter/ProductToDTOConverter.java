package oshop.web.converter;

import oshop.model.Product;
import oshop.model.ProductCategory;

public class ProductToDTOConverter extends BaseEntityConverter<Product, Integer> {

    private EntityConverter<ProductCategory, Integer> productCategoryConverter = new BaseEntityConverter<ProductCategory, Integer>() {
        @Override
        protected Class<ProductCategory> entityClass() {
            return ProductCategory.class;
        }

        @Override
        protected void convert(ProductCategory entity, ProductCategory convertedEntity) {
            convertedEntity.setLastUpdate(null);
            convertedEntity.setVersion(null);
            convertedEntity.setName(entity.getName());
        }
    };

    @Override
    protected Class<Product> entityClass() {
        return Product.class;
    }

    @Override
    protected void convert(Product entity, Product convertedEntity) throws Exception {
        convertedEntity.setName(entity.getName());
        convertedEntity.setImageId(entity.getImageId());
        convertedEntity.setPrice(entity.getPrice());
        convertedEntity.setCategory(productCategoryConverter.convert(entity.getCategory()));
    }
}
