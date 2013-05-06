package oshop.web.converter;

import oshop.model.Product;
import oshop.model.ProductCategory;

public class ProductConverter extends BaseEntityDetachConverter<Product, Integer> {

    private EntityDetachConverter<ProductCategory, Integer> productCategoryConverter = new BaseEntityDetachConverter<ProductCategory, Integer>() {
        @Override
        protected Class<ProductCategory> entityClass() {
            return ProductCategory.class;
        }

        @Override
        protected void detach(ProductCategory entity, ProductCategory detachedEntity) {
            detachedEntity.setLastUpdate(null);
            detachedEntity.setVersion(null);
            detachedEntity.setName(entity.getName());
        }
    };

    @Override
    protected Class<Product> entityClass() {
        return Product.class;
    }

    @Override
    protected void detach(Product entity, Product detachedEntity) throws Exception {
        detachedEntity.setName(entity.getName());
        detachedEntity.setImageId(entity.getImageId());
        detachedEntity.setPrice(entity.getPrice());
        detachedEntity.setCategory(productCategoryConverter.detach(entity.getCategory()));
    }
}
