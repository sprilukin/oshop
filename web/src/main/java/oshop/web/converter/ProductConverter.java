package oshop.web.converter;

import oshop.model.Product;

public class ProductConverter extends BaseEntityDetachConverter<Product, Integer> {

    @Override
    protected Class<Product> entityClass() {
        return Product.class;
    }

    @Override
    protected void detach(Product entity, Product detachedEntity) {
        detachedEntity.setName(entity.getName());
        detachedEntity.setImageId(entity.getImageId());
        detachedEntity.setPrice(entity.getPrice());
    }
}
