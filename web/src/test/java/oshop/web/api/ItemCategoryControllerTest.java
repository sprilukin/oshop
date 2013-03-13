package oshop.web.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import oshop.model.Item;
import oshop.model.ItemCategory;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ItemCategoryControllerTest extends BaseControllerTest {

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
    public void testListItemCategoriesWithFiltersAndSorters() throws Exception {
        addItemCategory("category1");
        addItemCategory("category2");
        addItemCategory("category3");

        MvcResult result = this.mockMvc.perform(
                get("/api/itemCategories/filter;name=category1,category2/sort;name=desc")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json")).andReturn();

        logResponse(result);
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

        logResponse(result);
    }
}
