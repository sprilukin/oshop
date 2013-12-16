package oshop.services.converter.order;

import org.springframework.stereotype.Component;
import oshop.model.AdditionalPayment;
import oshop.model.Customer;
import oshop.model.Discount;
import oshop.model.Order;
import oshop.model.OrderHasOrderStates;
import oshop.model.Product;
import oshop.model.ShippingAddress;
import oshop.services.converter.BaseEntityConverter;
import oshop.services.converter.EntityConverter;

import javax.annotation.Resource;

@Component
public class OrderToDTOConverter extends BaseEntityConverter<Order, Integer> {

    @Resource(name = "productForOrderToDTOConverter")
    private EntityConverter<Product, Integer> productsConverter;

    @Resource(name = "orderHasStateToDTOConverter")
    private EntityConverter<OrderHasOrderStates, Integer> orderStatesConverter;

    @Resource(name = "customerToDTOConverter")
    private EntityConverter<Customer, Integer> customerConverter;

    @Resource(name = "shippingAddressForOrderToDTOConverter")
    private EntityConverter<ShippingAddress, Integer> shippingAddressConverter;

    @Resource(name = "additionalPaymentToDTOConverter")
    private EntityConverter<AdditionalPayment, Integer> additionalPaymentConverter;

    @Resource(name = "discountToDTOConverter")
    private EntityConverter<Discount, Integer> discountConverter;

    @Override
    protected Class<Order> entityClass() {
        return Order.class;
    }

    @Override
    protected void convert(Order entity, Order convertedEntity) throws Exception {
        convertedEntity.setCurrentOrderStateName(entity.getCurrentOrderStateName());
        convertedEntity.setCurrentOrderStateDate(entity.getCurrentOrderStateDate());
        convertedEntity.setProductsCount(entity.getProductsCount());
        convertedEntity.setProductsPrice(entity.getProductsPrice());
        convertedEntity.setProducts(productsConverter.convert(entity.getProducts()));
        convertedEntity.setStates(orderStatesConverter.convert(entity.getStates()));
        convertedEntity.setTotalPrice(entity.getTotalPrice());
        convertedEntity.setCustomer(customerConverter.convert(entity.getCustomer()));
        convertedEntity.setDate(entity.getDate());
        convertedEntity.setDescription(entity.getDescription());
        convertedEntity.setDiscount(discountConverter.convert(entity.getDiscount()));
        convertedEntity.setAdditionalPayment(additionalPaymentConverter.convert(entity.getAdditionalPayment()));
        convertedEntity.setShippingAddress(shippingAddressConverter.convert(entity.getShippingAddress()));
    }
}
