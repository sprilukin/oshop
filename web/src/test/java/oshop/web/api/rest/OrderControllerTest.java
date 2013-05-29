package oshop.web.api.rest;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import oshop.model.Customer;
import oshop.model.EntityUtils;
import oshop.model.Order;
import oshop.model.OrderHasOrderStates;
import oshop.model.OrderState;
import oshop.model.Product;
import oshop.model.ProductCategory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderControllerTest extends BaseControllerTest {

    @Test
    public void testAddOrder() throws Exception {
        Customer customer = addCustomer("customer");

        Order order = new Order();
        order.setCustomer(customer);

        String customerAsString = mapper.writeValueAsString(order);

        MvcResult result = this.mockMvc.perform(
                post("/api/orders/")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerAsString))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.states").isArray())
                .andExpect(jsonPath("$.products").isArray())
                .andExpect(jsonPath("$.customer.name").value("customer"))
                .andReturn();

        logResponse(result);
    }

    @Test
    public void testAddProductsToOrder() throws Exception {
        ProductCategory productCategory = addProductCategory("category1");

        List<Product> products = addProducts(createProduct(productCategory, "Product1", new BigDecimal(10.01)),
                createProduct(productCategory, "Product2", new BigDecimal(10.1)),
                createProduct(productCategory, "Product3", new BigDecimal(10.2)));

        Order order = addOrder("customer", null);

        StringBuilder ids = new StringBuilder();
        for (Product product: products) {
            if (ids.length() > 0) {
                ids.append(",");
            }

            ids.append(product.getId());
        }

        this.mockMvc.perform(
                post("/api/orders/{id}/products/batch;ids={ids}/add", order.getId(), ids)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        orderDao.getSession().evict(order);
        order = orderDao.get(order.getId());

        assertEquals(3, order.getProducts().size());
        assertEquals(3, order.getProductsCount().intValue());
    }

    @Test
    public void testRemoveProductsFromOrder() throws Exception {
        ProductCategory productCategory = addProductCategory("category1");

        List<Product> products = addProducts(createProduct(productCategory, "Product1", new BigDecimal(10.01)),
                createProduct(productCategory, "Product2", new BigDecimal(10.1)),
                createProduct(productCategory, "Product3", new BigDecimal(10.2)));

        Order order = addOrder("customer", products);

        StringBuilder ids = new StringBuilder();
        for (Product product: products) {
            if (ids.length() > 0) {
                ids.append(",");
            }

            ids.append(product.getId());
        }

        this.mockMvc.perform(
                delete("/api/orders/{id}/products/batch;ids={ids}/delete", order.getId(), ids)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        orderDao.getSession().evict(order);

        order = orderDao.get(order.getId());
        assertEquals(0, order.getProducts().size());
        assertEquals(0, order.getProductsCount().intValue());
    }

    @Test
    public void testRemoveSingleProductFromOrder() throws Exception {
        ProductCategory productCategory = addProductCategory("category1");

        List<Product> products = addProducts(createProduct(productCategory, "Product1", new BigDecimal(10.01)),
                createProduct(productCategory, "Product2", new BigDecimal(10.1)),
                createProduct(productCategory, "Product3", new BigDecimal(10.2)));

        Order order = addOrder("customer", products);

        this.mockMvc.perform(
                delete("/api/orders/{id}/products/batch;ids={ids}/delete", order.getId(), products.get(0).getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        orderDao.getSession().evict(order);

        order = orderDao.get(order.getId());
        assertEquals(2, order.getProducts().size());
        assertEquals(2, order.getProductsCount().intValue());
    }

    @Test
    public void testRemoveOrder() throws Exception {
        ProductCategory productCategory = addProductCategory("category1");

        List<Product> products = addProducts(createProduct(productCategory, "Product1", new BigDecimal(10.01)),
                createProduct(productCategory, "Product2", new BigDecimal(10.1)),
                createProduct(productCategory, "Product3", new BigDecimal(10.2)));

        Order order = addOrder("customer", products);

        this.mockMvc.perform(
                delete("/api/orders/{id}", order.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        orderDao.getSession().flush();
        orderDao.getSession().clear();

        assertEquals(3, productDao.list(null, null).size());
        assertEquals(0, orderDao.list(null, null).size());
    }

    @Test
    public void testListOrderProducts() throws Exception {
        ProductCategory productCategory = addProductCategory("category1");

        addProduct(createProduct(productCategory, "Product0", new BigDecimal(10.01)));
        List<Product> products = addProducts(
                createProduct(productCategory, "Product1", new BigDecimal(10.01)),
                createProduct(productCategory, "Product2", new BigDecimal(10.1)),
                createProduct(productCategory, "Product3", new BigDecimal(10.2)));

        Order order = addOrder("customer", products);

        MvcResult result = this.mockMvc.perform(
                get("/api/orders/{id}/products", order.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size").value(3))
                .andExpect(jsonPath("$.values[0].name").value("Product1"))
                .andExpect(jsonPath("$.values[1].name").value("Product2"))
                .andExpect(jsonPath("$.values[2].name").value("Product3"))
                .andReturn();

        orderDao.getSession().flush();
        orderDao.getSession().clear();

        order = orderDao.get(order.getId());
        assertEquals(EntityUtils.round(new BigDecimal(30.31)), order.getProductsPrice());

        logResponse(result);
    }

    @Test
    public void testAddOrderHasState() throws Exception {

        Order order = addOrder("customer", null);
        OrderState newState = addOrderState("NEW");
        OrderState shippedState = addOrderState("SHIPPED");

        OrderHasOrderStates state1 = createOrderHasStates("", new Date(0), newState);
        OrderHasOrderStates state2 = createOrderHasStates("", new Date(1), shippedState);

        String stateAsString = mapper.writeValueAsString(state1);

        this.mockMvc.perform(
                post("/api/orders/{id}/orderHasStates", order.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stateAsString))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.date").value(0))
                .andExpect(jsonPath("$.description").value(""))
                .andExpect(jsonPath("$.orderState.name").value("NEW"));

        stateAsString = mapper.writeValueAsString(state2);

        this.mockMvc.perform(
                post("/api/orders/{id}/orderHasStates", order.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stateAsString))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.date").value(1))
                .andExpect(jsonPath("$.description").value(""))
                .andExpect(jsonPath("$.orderState.name").value("SHIPPED"));

        orderDao.getSession().flush();
        orderDao.getSession().clear();

        order = orderDao.get(order.getId());
        assertEquals("SHIPPED", order.getCurrentOrderStateName());
        assertEquals(2, order.getStates().size());

        MvcResult result = this.mockMvc.perform(
                get("/api/orders/{id}/orderHasStates", order.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size").value(2))
                .andExpect(jsonPath("$.values[0].orderState.name").value("NEW"))
                .andExpect(jsonPath("$.values[1].orderState.name").value("SHIPPED"))
                .andReturn();

        logResponse(result);
    }

    @Test
    public void testGetOrderByCustomer() throws Exception {
        Order order1 = addOrder("customer1", null);
        Order order2 = addOrder("customer2", null);

        MvcResult result = this.mockMvc.perform(
                get("/api/customers/" + order1.getCustomer().getId() + "/orders")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size").value(1))
                .andExpect(jsonPath("$.values[0].customer.name").value("customer1"))
                .andReturn();

        logResponse(result);
    }

    @Test
    public void testGetOrderByCustomerWithFilter() throws Exception {
        Order order1 = addOrder("customer1", null);
        Order order2 = addOrder("customer2", null);

        MvcResult result = this.mockMvc.perform(
                get("/api/customers/" + order1.getCustomer().getId() + "/orders/filter;customer=customer;/sorter;")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size").value(1))
                .andExpect(jsonPath("$.values[0].customer.name").value("customer1"))
                .andReturn();

        logResponse(result);
    }

    @Test
    public void testGetOrderByCustomerWithFilterForOtherCustomer() throws Exception {
        Order order1 = addOrder("customer1", null);
        Order order2 = addOrder("customer2", null);

        MvcResult result = this.mockMvc.perform(
                get("/api/customers/" + order1.getCustomer().getId() + "/orders/filter;customer=customer2;/sorter;")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        logResponse(result);
    }
}
