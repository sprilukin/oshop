package oshop.web.api.rest;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import oshop.model.Customer;
import oshop.model.Order;
import oshop.model.Product;
import oshop.model.ProductCategory;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
}
