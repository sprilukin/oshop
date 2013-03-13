package oshop.web.api.rest;

import org.junit.Test;
import org.springframework.http.MediaType;
import oshop.model.Item;
import oshop.model.ItemCategory;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ItemControllerTest extends BaseControllerTest {

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
}
