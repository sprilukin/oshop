package oshop.web.api.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import oshop.dao.GenericDao;
import oshop.model.Product;
import oshop.model.ProductCategory;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({
        "classpath*:spring-mvc-config.xml",
        "classpath*:applicationContext.xml",
        "classpath:META-INF/applicationContext-sessionFactory.xml",
        "classpath:META-INF/applicationContext-testDataSource.xml",
        "classpath:META-INF/applicationContext-dao.xml"
})
@Transactional
@TransactionConfiguration(
        defaultRollback = true,
        transactionManager = "transactionManager")
public abstract class BaseControllerTest {

    protected static final Log log = LogFactory.getLog(BaseControllerTest.class);

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private GenericDao<ProductCategory, Integer> productCategoryDao;

    @Resource
    private GenericDao<Product, Integer> productDao;

    @Resource
    protected MessageSource messageSource;

    protected ObjectMapper mapper = new ObjectMapper();
    protected MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    protected void logResponse(MvcResult result) {
        log.debug(new String(result.getResponse().getContentAsByteArray()));
    }

    protected ProductCategory addProductCategory(String name) throws Exception {
        ProductCategory category = new ProductCategory();
        category.setName(name);

        category.setId(productCategoryDao.add(category));
        return category;
    }

    protected void addProductCategories(String... names) throws Exception {
        for (String name: names) {
            addProductCategory(name);
        }
    }

    protected Product addProduct(Product product) throws Exception {
        product.setId(productDao.add(product));
        return product;
    }

    protected void addProducts(Product... poducts) throws Exception {
        for (Product poduct: poducts) {
            addProduct(poduct);
        }
    }

    protected Product createProduct(ProductCategory category, String name, BigDecimal price) {
        Product product = new Product();

        ProductCategory cat = new ProductCategory();
        cat.setId(category.getId());
        product.setCategory(cat);
        product.setName(name);
        product.setPrice(price);

        return product;
    }
}
