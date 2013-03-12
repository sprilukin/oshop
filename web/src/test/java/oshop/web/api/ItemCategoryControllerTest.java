package oshop.web.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import oshop.model.ItemCategory;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({
        "classpath*:spring-mvc-config.xml",
        "classpath*:applicationContext.xml",
        "classpath*:META-INF/applicationContext*.xml"})
@Transactional
@TransactionConfiguration(
        defaultRollback = true,
        transactionManager = "transactionManager")
public class ItemCategoryControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testAddCategory() throws Exception {
        ItemCategory category = new ItemCategory();
        category.setName("name1");

        ObjectMapper mapper = new ObjectMapper();
        String categoryAsString = mapper.writeValueAsString(category);

        this.mockMvc.perform(
                put("/api/itemCategories/add")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryAsString))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name").value("name1"));
    }

    @Test
    public void testAddCategoryWithNullName() throws Exception {
        ItemCategory category = new ItemCategory();

        ObjectMapper mapper = new ObjectMapper();
        String categoryAsString = mapper.writeValueAsString(category);

        this.mockMvc.perform(
                put("/api/itemCategories/add")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryAsString))
                .andExpect(status().isBadRequest());
                //.andExpect(content().contentType("application/json"))
                //.andExpect(jsonPath("$.message").value("Category name can not be null"));
    }
}
