package oshop.dao;

import org.junit.Test;
import oshop.model.Customer;

import javax.annotation.Resource;

import static org.junit.Assert.assertNotNull;

public class CustomerDaoTest extends BaseDaoTest {


    @Resource
    private GenericDao<Customer, Integer> customerDao;

    @Test
    public void ttt() {
        Customer customer = new Customer();
        customer.setName("Name");
        customer.setSurname("Surname");
        customer.setFatherName("FatherName");
        customer.setPhoneNumber("234345345345");

        Integer id = customerDao.add(customer);

        assertNotNull(id);
    }

    @Test
    public void ttt2() throws Exception{
        setUpDb("oshop/dao/dataSet.xml");
        Customer customer = customerDao.get(1);

        assertNotNull(customer);
    }
}
