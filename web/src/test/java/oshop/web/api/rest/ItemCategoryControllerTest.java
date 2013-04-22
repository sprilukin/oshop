package oshop.web.api.rest;

import org.junit.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import oshop.model.ItemCategory;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ItemCategoryControllerTest extends BaseControllerTest {

    @Test
    public void testAddCategory() throws Exception {
        ItemCategory category = new ItemCategory();
        category.setName("name1");

        String categoryAsString = mapper.writeValueAsString(category);

        this.mockMvc.perform(
                post("/api/itemCategories/")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryAsString))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("name1"));
    }

    @Test
    public void testAddCategoryWithNullName() throws Exception {
        ItemCategory category = new ItemCategory();
        String categoryAsString = mapper.writeValueAsString(category);

        this.mockMvc.perform(
                post("/api/itemCategories/")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryAsString))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("Validation failed"));
                //It is not stable order to test mesages - sometime "may not be empty" is first some times is not
                //.andExpect(jsonPath("$.fields.name.[0]").value("may not be empty"))
                //.andExpect(jsonPath("$.fields.name.[1]").value("may not be null"));
    }

    @Test
    public void testGetItemCategoryById() throws Exception {
        Integer id = addItemCategory("category1").getId();

        this.mockMvc.perform(
                get("/api/itemCategories/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("category1"))
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    public void testGetItemCategoryByIdNotExists() throws Exception {
        Integer id = addItemCategory("category1").getId();

       this.mockMvc.perform(
                get("/api/itemCategories/" + (id + 1))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("\"" + messageSource.getMessage("error.entity.by.id.not.found",
                        new Object[]{id}, LocaleContextHolder.getLocale()) + "\""));
    }

    @Test
    public void testRemove() throws Exception {
        Integer id = addItemCategory("category1").getId();

        this.mockMvc.perform(
                delete("/api/itemCategories/" + id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdate() throws Exception {
        ItemCategory itemCategory = addItemCategory("category1");
        itemCategory.setName("category2");

        this.mockMvc.perform(
                put("/api/itemCategories/" + itemCategory.getId())
                        .content(mapper.writeValueAsString(itemCategory))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("category2"));
    }

    @Test
    public void testRemoveNotExisting() throws Exception {
        this.mockMvc.perform(
                delete("/api/itemCategories/" + 1)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testListItemCategories() throws Exception {
        addItemCategories("category1", "category2", "category3");

        this.mockMvc.perform(
                get("/api/itemCategories/")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size").value(3))
                .andExpect(jsonPath("$.values.[0].name").value("category1"))
                .andExpect(jsonPath("$.values.[1].name").value("category2"))
                .andExpect(jsonPath("$.values.[2].name").value("category3"));

        this.mockMvc.perform(
                get("/api/itemCategories/?offset=1&limit=1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size").value(3))
                .andExpect(jsonPath("$.values.[0].name").value("category2"));
    }

    @Test
    public void testListEmptyItemCategories() throws Exception {
        this.mockMvc.perform(
                get("/api/itemCategories/")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().bytes(new byte[]{}));
    }

    @Test
    public void testListItemCategoriesWithFiltersAndSorters() throws Exception {
        addItemCategories("category1", "category2", "category3");

        this.mockMvc.perform(
                get("/api/itemCategories/filter;name=category1,category3/sort;name=desc")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size").value(2))
                .andExpect(jsonPath("$.values.[0].name").value("category3"))
                .andExpect(jsonPath("$.values.[1].name").value("category1"));

        //Test filter only
        this.mockMvc.perform(
                get("/api/itemCategories/filter;name=category2/sort")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size").value(1))
                .andExpect(jsonPath("$.values.[0].name").value("category2"));

        //Test sort only
        this.mockMvc.perform(
                get("/api/itemCategories/filter/sort;name=asc?limit=1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size").value(3))
                .andExpect(jsonPath("$.values.[0].name").value("category1"));
    }

    @Test
    public void testEmptyListItemCategoriesWithFiltersAndSorters() throws Exception {

        this.mockMvc.perform(
                get("/api/itemCategories/filter;name=category1,category3/sort;name=desc")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testListItemCategoriesById() throws Exception {
        Integer id = addItemCategory("category1").getId();
        addItemCategories("category2", "category3");

        this.mockMvc.perform(
                get("/api/itemCategories/" + id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("category1"));
    }

    @Test
    public void testListItems() throws Exception {
        ItemCategory itemCategory = addItemCategory("category1");

        addItems(
                createItem(itemCategory, "item1", new BigDecimal(10.1)),
                createItem(itemCategory, "item2", new BigDecimal(10.01)),
                createItem(itemCategory, "item3", new BigDecimal(10.001)));

        MvcResult result = this.mockMvc.perform(
                get("/api/itemCategories/" + itemCategory.getId() + "/items").accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("item1"))
                .andExpect(jsonPath("$[1].name").value("item2"))
                .andExpect(jsonPath("$[2].name").value("item3"))
                .andExpect(jsonPath("$[0].price").value(10.1))
                .andExpect(jsonPath("$[1].price").value(10.01))
                .andExpect(jsonPath("$[2].price").value(10.0)).andReturn();

        logResponse(result);
    }

    @Test
    public void testListItemsWithOrderingAndFiltering() throws Exception {
        ItemCategory itemCategory = addItemCategory("category1");

        addItems(createItem(itemCategory, "item1", new BigDecimal(10.01)),
                createItem(itemCategory, "item2", new BigDecimal(10.1)),
                createItem(itemCategory, "item3", new BigDecimal(10.2)));

        this.mockMvc.perform(
                get("/api/itemCategories/" + itemCategory.getId() + "/items/filter;name=item/sort;name=asc")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("item1"))
                .andExpect(jsonPath("$[1].name").value("item2"))
                .andExpect(jsonPath("$[2].name").value("item3"))
                .andExpect(jsonPath("$[0].price").value(10.01))
                .andExpect(jsonPath("$[1].price").value(10.1))
                .andExpect(jsonPath("$[2].price").value(10.2));
    }
}
