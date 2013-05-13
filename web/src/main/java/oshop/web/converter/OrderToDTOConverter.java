package oshop.web.converter;

import org.springframework.stereotype.Component;
import oshop.model.Customer;
import oshop.model.Order;
import oshop.model.OrderHasOrderStates;
import oshop.model.Product;

import javax.annotation.Resource;

@Component
public class OrderToDTOConverter extends BaseEntityConverter<Order, Integer> {

    @Resource(name = "productToDTOConverter")
    private EntityConverter<Product, Integer> productsConverter;

    @Resource(name = "orderHasStateToDTOConverter")
    private EntityConverter<OrderHasOrderStates, Integer> orderStatesConverter;

    @Resource(name = "customerToDTOConverter")
    private EntityConverter<Customer, Integer> customerConverter;

    protected void convertInternal(Order entity, Order convertedEntity) throws Exception {
        convertedEntity.setCurrentOrderStateName(entity.getCurrentOrderStateName());
        convertedEntity.setProductsCount(entity.getProductsCount());
        convertedEntity.setProductsPrice(entity.getProductsPrice());
        convertedEntity.setCustomer(customerConverter.convert(entity.getCustomer()));
        convertedEntity.setDate(entity.getDate());
        convertedEntity.setDiscount(null); //TODO
        convertedEntity.setPrepayment(null); //TODO
        convertedEntity.setShippingAddress(null); //TODO
    }

    @Override
    protected Class<Order> entityClass() {
        return Order.class;
    }

    @Override
    protected void convert(Order entity, Order convertedEntity) throws Exception {
        convertInternal(entity, convertedEntity);

        convertedEntity.setProducts(productsConverter.convert(entity.getProducts()));
        convertedEntity.setStates(orderStatesConverter.convert(entity.getStates()));
    }

    protected Order convertForCollection(Order entity) throws Exception {
        Order convertedEntity = newInstance();
        convertedEntity.setId(entity.getId());
        //convertedEntity.setLastUpdate(entity.getLastUpdate());
        convertedEntity.setVersion(entity.getVersion());

        convertInternal(entity, convertedEntity);
        return convertedEntity;
    }
}
