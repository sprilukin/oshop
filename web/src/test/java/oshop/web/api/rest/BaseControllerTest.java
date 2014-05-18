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
import oshop.model.AdditionalPayment;
import oshop.model.City;
import oshop.model.Customer;
import oshop.model.Discount;
import oshop.model.Order;
import oshop.model.OrderHasOrderStates;
import oshop.model.OrderState;
import oshop.model.Product;
import oshop.model.ProductCategory;
import oshop.model.ShippingAddress;
import oshop.model.ShippingType;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({
        "classpath*:spring-mvc-config.xml",
        "classpath*:applicationContext.xml",
        "classpath:META-INF/applicationContext-sessionFactory.xml",
        "classpath:META-INF/applicationContext-testDataSource.xml",
        "classpath:META-INF/applicationContext-projections.xml",
        "classpath:META-INF/applicationContext-filters.xml",
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
    protected GenericDao<OrderState, Integer> orderStateDao;

    @Resource
    protected GenericDao<OrderHasOrderStates, Integer> orderHasOrderStatesDao;

    @Resource
    protected GenericDao<AdditionalPayment, Integer> additionalPaymentDao;

    @Resource
    protected GenericDao<Discount, Integer> discountDao;

    @Resource
    protected GenericDao<City, Integer> cityDao;

    @Resource
    protected GenericDao<ShippingType, Integer> shippingTypeDao;

    @Resource
    protected GenericDao<ShippingAddress, Integer> shippingAddressDao;

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

    protected OrderState addOrderState(String name) {
        OrderState state = new OrderState();
        state.setName(name);
        state.setId(orderStateDao.add(state));

        return state;
    }

    protected AdditionalPayment addAdditionalPayment(BigDecimal amount, String description) {
        AdditionalPayment ap = new AdditionalPayment();
        ap.setAmount(amount);
        ap.setDescription(description);
        ap.setId(additionalPaymentDao.add(ap));

        return ap;
    }

    protected Discount addDiscount(BigDecimal amount, String description, Byte type) {
        Discount discount = new Discount();
        discount.setAmount(amount);
        discount.setDescription(description);
        discount.setType(type);
        discount.setId(discountDao.add(discount));

        return discount;
    }

    protected City addCity(String name, String region) {
        City city = new City();
        city.setName(name);
        city.setRegion(region);
        city.setId(cityDao.add(city));

        return city;
    }

    protected ShippingType addShippingType(String type) {
        ShippingType shippingType = new ShippingType();
        shippingType.setName(type);
        shippingType.setId(shippingTypeDao.add(shippingType));

        return shippingType;
    }

    protected ShippingAddress addShippingAddress(String address, String cityName, Customer customer, String shippingType, String recipient) {
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setAddress(address);
        shippingAddress.setCity(addCity(cityName, "region"));
        shippingAddress.setCustomer(customer);
        shippingAddress.setShippingType(addShippingType(shippingType));
        shippingAddress.setRecipient(recipient);

        shippingAddress.setId(shippingAddressDao.add(shippingAddress));

        return shippingAddress;
    }

    protected OrderHasOrderStates createOrderHasStates(String descr, Date date, OrderState state) {
        OrderHasOrderStates orderStates = new OrderHasOrderStates();
        orderStates.setDescription(descr);
        orderStates.setDate(date);
        orderStates.setOrderState(state);

        return orderStates;
    }

}
