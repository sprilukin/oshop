package oshop.dao;

import org.junit.Test;
import oshop.model.Customer;
import oshop.model.Order;
import oshop.model.OrderHasOrderStates;
import oshop.model.OrderState;
import oshop.model.Product;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderDaoTest extends BaseDaoTest {


    @Resource
    private GenericDaoImpl<Order, Integer> orderDao;

    @Resource
    private GenericDao<Customer, Integer> customerDao;

    @Resource
    private GenericDao<Product, Integer> productDao;

    @Resource
    private GenericDao<OrderState, Integer> orderStateDao;

    @Test
    public void testAddProductsToOrder() throws Exception{
        setUpDb("oshop/dao/order.xml");

        Customer customer = customerDao.list(null, null).get(0);
        List<Product> products = productDao.list(null, null);

        Order order = new Order();
        order.setCustomer(customer);
        order.getProducts().addAll(products);

        orderDao.add(order);
        orderDao.flush();
        orderDao.getSession().evict(order);

        List<Order> orders = orderDao.list(null, null);
        assertEquals(2, orders.get(0).getProducts().size());
        assertEquals(2, orders.get(0).getProductsCount().intValue());
    }

    @Test
    public void testAddStatesToOrder() throws Exception{
        setUpDb("oshop/dao/order.xml");

        Customer customer = customerDao.list(null, null).get(0);


        OrderState state1 = orderStateDao.get(1);
        OrderState state2 = orderStateDao.get(2);

        OrderHasOrderStates orderHasOrderStates1 = new OrderHasOrderStates();
        orderHasOrderStates1.setDate(new Date());
        orderHasOrderStates1.setDescription("Test description1");
        orderHasOrderStates1.setOrderState(state1);

        OrderHasOrderStates orderHasOrderStates2 = new OrderHasOrderStates();
        orderHasOrderStates2.setDate(new Date());
        orderHasOrderStates2.setDescription("Test description2");
        orderHasOrderStates2.setOrderState(state2);

        Order order = new Order();
        order.setCustomer(customer);
        order.getStates().add(orderHasOrderStates1);
        order.getStates().add(orderHasOrderStates2);

        orderHasOrderStates1.setOrder(order);
        orderHasOrderStates2.setOrder(order);

        orderDao.add(order);

        orderDao.getSession().evict(order);

        List<Order> orders = orderDao.list(null, null);
        assertEquals(2, orders.get(0).getStates().size());
        assertEquals("SHIPPED", orders.get(0).getCurrentOrderStateName());
    }
}
