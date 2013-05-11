package oshop.web.converter;

import org.springframework.stereotype.Component;
import oshop.model.OrderHasProducts;
import oshop.model.Product;

import javax.annotation.Resource;

@Component
public class OrderHasProductsToDTOConverter extends BaseEntityConverter<OrderHasProducts, Integer> {

    @Resource(name = "productToDTOConverter")
    private EntityConverter<Product, Integer> productConverter;

    @Override
    protected Class<OrderHasProducts> entityClass() {
        return OrderHasProducts.class;
    }

    @Override
    protected void convert(OrderHasProducts entity, OrderHasProducts convertedEntity) throws Exception {
        convertedEntity.setProduct(productConverter.convert(entity.getProduct()));
    }
}
