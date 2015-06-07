package oshop.web.api.rest;

import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ImageControllerTest extends BaseControllerTest {

    @Test
    public void testAddImage() throws Exception {
        String contentType = "image/jpeg";
        byte[] imageData = new byte[10];

        this.mockMvc.perform(
                post("/api/images/raw")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.parseMediaType(contentType))
                        .content(imageData))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
