package oshop.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import oshop.model.Discount;
import oshop.model.OrderState;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DictionariesTest extends BaseDaoTest {


    @Resource
    private GenericDao<OrderState, Integer> saleStateDao;

    @Resource
    private GenericDao<Discount, Integer> discountDao;

    @Test
    public void testOrderStateItems() throws Exception {
        setUpDb("oshop/dao/orderState.xml");

        List<OrderState> orderStateList =  saleStateDao.list(null, null);
        assertEquals(6, orderStateList.size());

        Criteria criteria =  saleStateDao.createCriteria();
        criteria.add(Restrictions.eq("name", "CANCELED"));

        OrderState state = saleStateDao.get(criteria);
        assertEquals("CANCELED", state.getName());
    }

    @Test
    public void testDiscountItems() throws Exception {
        setUpDb("oshop/dao/discount.xml");

        List<Discount> discountTypeList =  discountDao.list(null, null);
        assertEquals(3, discountTypeList.size());
    }
}
