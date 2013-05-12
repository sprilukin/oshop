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

        Order order = addOrder("customer");

        StringBuilder ids = new StringBuilder();
        for (Product product: products) {
            if (ids.length() > 0) {
                ids.append(",");
            }

            ids.append(product.getId());
        }

        MvcResult result = this.mockMvc.perform(
                post("/api/orders/" + order.getId() + "/products/batch;ids=" + ids + "/add")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.states").isArray())
                .andExpect(jsonPath("$.products[(@.length - 1)].name").value("Product3"))
                .andExpect(jsonPath("$.productsCount").value(3))
                .andExpect(jsonPath("$.customer.name").value("customer"))
                .andReturn();

        logResponse(result);
    }
}
