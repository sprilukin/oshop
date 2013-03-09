package oshop.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import oshop.model.Customer;

import javax.annotation.Resource;

import static org.junit.Assert.assertNotNull;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-persistence.xml")
@TransactionConfiguration(defaultRollback=true, transactionManager="transactionManager")
public class CustomerDaoTest {

    @Resource
    private CustomerDao customerDao;

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
    public void ttt2() {
        Customer customer = new Customer();
        customer.setName("Name");
        customer.setSurname("Surname");
        customer.setFatherName("FatherName");
        customer.setPhoneNumber("234345345345");

        Integer id = customerDao.add(customer);

        assertNotNull(id);
    }
}
