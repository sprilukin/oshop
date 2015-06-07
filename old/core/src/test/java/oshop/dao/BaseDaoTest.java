package oshop.dao;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.SessionFactory;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.sql.Connection;

import static org.junit.Assert.assertNotNull;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:META-INF/applicationContext-sessionFactory.xml",
        "classpath:META-INF/applicationContext-testDataSource.xml",
        "classpath:META-INF/applicationContext-dao.xml"})
@TransactionConfiguration(
        defaultRollback = true,
        transactionManager = "transactionManager")
public abstract class BaseDaoTest {

    @Resource
    private SessionFactory sessionFactory;

    protected void setUpDb(String pathToDataSet) throws Exception {
        setUpDb(pathToDataSet, DatabaseOperation.CLEAN_INSERT);
    }

    protected void setUpDb(String pathToDataSet, DatabaseOperation operation) throws Exception {
        operation.execute(getConnection(), getDataSet(pathToDataSet));
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
