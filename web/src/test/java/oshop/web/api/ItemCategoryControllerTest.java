package oshop.web.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import oshop.model.Item;
import oshop.model.ItemCategory;

import java.math.BigDecimal;
import java.util.Arrays;

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

    private static final Log log = LogFactory.getLog(ItemCategoryControllerTest.class);

    private ObjectMapper mapper = new ObjectMapper();

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

    @Test
    public void testAddItemWithCategory() throws Exception {
        ItemCategory itemCategory = addItemCategory("category1");

        Item item = createItem(itemCategory, "item1", new BigDecimal(10));

        this.mockMvc.perform(
                put("/api/items/add")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(item)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name").value("item1"))
                .andExpect(jsonPath("$.price").value(10));
    }

    @Test
    public void testListItems() throws Exception {
        ItemCategory itemCategory = addItemCategory("category1");

        Item item = createItem(itemCategory, "item1", new BigDecimal(10));
        for (Item i: Arrays.asList(item, item, item)) {
            addItem(i);
        }

        MvcResult result = this.mockMvc.perform(
                get("/api/itemCategories/" + itemCategory.getId() + "/items").accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].name").value("item1"))
                .andExpect(jsonPath("$[1].name").value("item1"))
                .andExpect(jsonPath("$[2].name").value("item1"))
                .andExpect(jsonPath("$[2].price").value(10))
                .andExpect(jsonPath("$[2].price").value(10))
                .andExpect(jsonPath("$[2].price").value(10)).andReturn();

        log.debug(new String(result.getResponse().getContentAsByteArray()));
    }

    private ItemCategory addItemCategory(String name) throws Exception {
        ItemCategory category = new ItemCategory();
        category.setName(name);

        String categoryAsString = mapper.writeValueAsString(category);
        MvcResult result = this.mockMvc.perform(
                put("/api/itemCategories/add")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryAsString)).andReturn();

        return mapper.readValue(result.getResponse().getContentAsByteArray(), ItemCategory.class);
    }

    private Item addItem(Item item) throws Exception {
        String itemAsString = mapper.writeValueAsString(item);
        MvcResult result = this.mockMvc.perform(
                put("/api/items/add")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(itemAsString)).andReturn();

        return mapper.readValue(result.getResponse().getContentAsByteArray(), Item.class);
    }

    private Item createItem(ItemCategory category, String name, BigDecimal price) {
        Item item = new Item();

        ItemCategory cat = new ItemCategory();
        cat.setId(category.getId());
        item.setCategory(cat);
        item.setName(name);
        item.setPrice(price);

        return item;
    }
}
