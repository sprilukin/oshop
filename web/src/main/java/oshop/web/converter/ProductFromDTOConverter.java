package oshop.web.converter;

import oshop.dao.GenericDao;
import oshop.model.Product;
import oshop.model.ProductCategory;

public class ProductFromDTOConverter extends BaseEntityConverter<Product, Integer> {

    private GenericDao<ProductCategory, Integer> productCategoryDao;

    public ProductFromDTOConverter(GenericDao<ProductCategory, Integer> productCategoryDao) {
        this.productCategoryDao = productCategoryDao;
    }

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
        convertedEntity.setCategory(productCategoryDao.get(entity.getCategory().getId()));
    }
}
