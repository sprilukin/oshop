package oshop.web.api.rest;

import org.junit.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import oshop.model.ItemCategory;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ImageControllerTest extends BaseControllerTest {

    @Test
    public void testAddImage() throws Exception {
        String contentType = "image/jpeg";
        byte[] imageData = new byte[10];

        this.mockMvc.perform(
                post("/api/images/")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.parseMediaType(contentType))
                        .content(imageData))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testAddImageWithNullContentType() throws Exception {
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
    public void testAddImageWithWrongContentType() throws Exception {
    }

    @Test
    public void testAddImageWithNullData() throws Exception {
    }

    @Test
    public void testGetImageById() throws Exception {
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
    public void testGetImageByIdNotExists() throws Exception {
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
                .andExpect(status().isNoContent());
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
}
