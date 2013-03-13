package oshop.web.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import oshop.model.Item;
import oshop.model.ItemCategory;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

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
public abstract class BaseControllerTest {

    protected static final Log log = LogFactory.getLog(BaseControllerTest.class);

    protected ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private WebApplicationContext wac;

    protected MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    protected void logResponse(MvcResult result) {
        log.debug(new String(result.getResponse().getContentAsByteArray()));
    }

    protected ItemCategory addItemCategory(String name) throws Exception {
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

    protected void addItemCategories(String... names) throws Exception {
        for (String name: names) {
            addItemCategory(name);
        }
    }

    protected Item addItem(Item item) throws Exception {
        String itemAsString = mapper.writeValueAsString(item);
        MvcResult result = this.mockMvc.perform(
                put("/api/items/add")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(itemAsString)).andReturn();

        return mapper.readValue(result.getResponse().getContentAsByteArray(), Item.class);
    }

    protected Item createItem(ItemCategory category, String name, BigDecimal price) {
        Item item = new Item();

        ItemCategory cat = new ItemCategory();
        cat.setId(category.getId());
        item.setCategory(cat);
        item.setName(name);
        item.setPrice(price);

        return item;
    }
}
