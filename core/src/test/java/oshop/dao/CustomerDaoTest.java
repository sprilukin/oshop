package oshop.dao;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import oshop.model.Customer;

import javax.annotation.Resource;

import java.io.InputStream;
import java.sql.Connection;

import static org.junit.Assert.assertNotNull;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext-persistence.xml", "classpath:applicationContext-dao.xml"})
@TransactionConfiguration(defaultRollback=true, transactionManager="transactionManager")
public class CustomerDaoTest {


    @Resource
    private SessionFactory sessionFactory;

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

    private void setUpDb(String pathToDataSet) throws Exception {
        DatabaseOperation.CLEAN_INSERT.execute(getConnection(), getDataSet(pathToDataSet));
    }

    private IDataSet getDataSet(String pathToDataSet) throws Exception {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(pathToDataSet);
        return new FlatXmlDataSetBuilder().build(inputStream);
    }

    private IDatabaseConnection getConnection() throws Exception {
        Connection jdbcConnection = SessionFactoryUtils.getDataSource(sessionFactory).getConnection();
        return new DatabaseConnection(jdbcConnection);
    }

}
