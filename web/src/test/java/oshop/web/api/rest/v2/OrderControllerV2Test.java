package oshop.web.api.rest.v2;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import oshop.model.*;
import oshop.services.converter.EntityConverter;
import oshop.web.api.rest.BaseControllerTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class OrderControllerV2Test extends BaseControllerTest {

    @Resource(name = "orderToDTOConverter")
    private EntityConverter<Order, Integer> orderFromDtoConverter;

    @Test
    public void testAddOrderWithItsRelations() throws Exception {
        Customer customer = addCustomer("customer");
        ProductCategory productCategory = addProductCategory("category1");
        OrderState orderState = addOrderState("state1");
        AdditionalPayment ap = addAdditionalPayment(new BigDecimal(10.0), "additionaPayment");
        Discount discount = addDiscount(new BigDecimal(10.0), "discount", Discount.Type.FIXED_DISCOUNT.getType());

        //products
        Product product1 = new Product();
        product1.setCategory(productCategory);
        product1.setName("product1");
        product1.setPrice(new BigDecimal(1));
        product1 = addProduct(product1);

        Product product2 = new Product();
        product2.setCategory(productCategory);
        product2.setName("product2");
        product2.setPrice(new BigDecimal(2));
        product2 = addProduct(product2);

        //OrderHasOrderStates
        OrderHasOrderStates os1 = createOrderHasStates("os1", new Date(100), orderState);
        OrderHasOrderStates os2 = createOrderHasStates("os2", new Date(1000), orderState);

        Order order = new Order();
        order.setCustomer(customer);
        order.setProducts(Arrays.asList(product1, product2));
        order.setStates(Arrays.asList(os1, os2));
        order.setAdditionalPayment(ap);
        order.setDiscount(discount);
        order.setDescription("Description");
        order.setDate(new Date(123));
        order.setShippingAddress(addShippingAddress("address1", "city1", customer, "shippingType1", "Recipient1"));


        String orderAsString = mapper.writeValueAsString(order);

        MvcResult result = this.mockMvc.perform(
                post("/api/v2/orders/")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderAsString)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.states").isArray())
                .andExpect(jsonPath("$.states[0].description").value("os1"))
                .andExpect(jsonPath("$.states[1].description").value("os2"))
                .andExpect(jsonPath("$.products").isArray())
                .andExpect(jsonPath("$.customer.name").value("customer"))
                .andExpect(jsonPath("$.additionalPayment.description").value("additionaPayment"))
                .andExpect(jsonPath("$.discount.description").value("discount"))
                .andExpect(jsonPath("$.shippingAddress.address").value("address1"))
                .andExpect(jsonPath("$.products[0].name").value("product1"))
                .andExpect(jsonPath("$.products[1].name").value("product2"))
                .andReturn();

        logResponse(result);
    }

    @Test
    public void testUpdateOrderWithItsRelations() throws Exception {
        testAddOrderWithItsRelations();

        Order order = orderFromDtoConverter.convert(orderDao.list(null, null).get(0));

        Customer customer2 = addCustomer("customer2");
        ProductCategory productCategory = addProductCategory("category2");
        OrderState orderState = addOrderState("state2");
        AdditionalPayment ap2 = addAdditionalPayment(new BigDecimal(10.0), "additionaPayment2");
        Discount discount2 = addDiscount(new BigDecimal(10.0), "discount2", Discount.Type.FIXED_DISCOUNT.getType());

        //products
        Product product3 = new Product();
        product3.setCategory(productCategory);
        product3.setName("product3");
        product3.setPrice(new BigDecimal(3));
        product3 = addProduct(product3);

        //OrderHasOrderStates
        OrderHasOrderStates os3 = createOrderHasStates("os3", new Date(3000), orderState);

        order.setCustomer(customer2);
        order.getProducts().remove(1);
        order.getProducts().add(product3);
        order.getStates().remove(0);
        order.getStates().add(os3);
        order.setAdditionalPayment(ap2);
        order.setDiscount(discount2);
        order.setDescription("Description2");
        order.setDate(new Date(1234));
        order.setShippingAddress(addShippingAddress("address2", "city2", customer2, "shippingType2", "Recipient2"));


        String orderAsString = mapper.writeValueAsString(order);

        MvcResult result = this.mockMvc.perform(
                put("/api/v2/orders/" + order.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderAsString)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.states").isArray())
                .andExpect(jsonPath("$.states[0].description").value("os2"))
                .andExpect(jsonPath("$.states[0].id").value(order.getStates().get(0).getId()))
                .andExpect(jsonPath("$.states[1].description").value("os3"))
                .andExpect(jsonPath("$.products").isArray())
                .andExpect(jsonPath("$.customer.name").value("customer2"))
                .andExpect(jsonPath("$.additionalPayment.description").value("additionaPayment2"))
                .andExpect(jsonPath("$.discount.description").value("discount2"))
                .andExpect(jsonPath("$.shippingAddress.address").value("address2"))
                .andExpect(jsonPath("$.products[0].name").value("product1"))
                .andExpect(jsonPath("$.products[1].name").value("product3"))
                .andReturn();

        logResponse(result);
    }
}
