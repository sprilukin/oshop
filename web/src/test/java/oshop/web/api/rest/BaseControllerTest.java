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
import oshop.model.Customer;
import oshop.model.Order;
import oshop.model.Product;
import oshop.model.ProductCategory;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    protected GenericDao<ProductCategory, Integer> productCategoryDao;

    @Resource
    protected GenericDao<Product, Integer> productDao;

    @Resource
    protected GenericDao<Customer, Integer> customerDao;

    @Resource
    protected GenericDao<Order, Integer> orderDao;

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

    protected List<Product> addProducts(Product... poducts) throws Exception {

        List<Product> products = new ArrayList<Product>();

        for (Product poduct: poducts) {
            products.add(addProduct(poduct));
        }

        return products;
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

    protected Customer addCustomer(String name) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setId(customerDao.add(customer));

        return customer;
    }

    protected Order addOrder(String name, List<Product> products) {
        Order order = new Order();
        order.setCustomer(addCustomer(name));

        if (products != null) {
            order.getProducts().addAll(products);
        }

        orderDao.add(order);

        if (products != null) {
            orderDao.getSession().flush();
            orderDao.getSession().evict(order);
        }

        return orderDao.get(order.getId());
    }
}
