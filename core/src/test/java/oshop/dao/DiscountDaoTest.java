package oshop.dao;

import org.junit.Test;
import oshop.model.Discount;

import javax.annotation.Resource;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DiscountDaoTest extends BaseDaoTest {


    @Resource
    private GenericDao<Discount, Integer> discountDao;

    @Test
    public void createPercentDiscount() {
        Discount discount = new Discount();
        discount.setDescription("test");
        discount.setAmount(new BigDecimal(10.0));
        discount.setType(Discount.Type.PERCENT_DISCOUNT.getType());

        Integer id = discountDao.add(discount);
        discountDao.getSession().evict(discount);
        discount = discountDao.get(id);

        assertNotNull(discount);
        assertEquals(10, discount.getAmount().intValue());
        assertEquals("test", discount.getDescription());
        assertEquals("%", discount.getTypeAsString());
        assertEquals(Discount.Type.PERCENT_DISCOUNT.getType(), (byte)discount.getType());
    }

    @Test
    public void createFixedDiscount() {
        Discount discount = new Discount();
        discount.setDescription("test2");
        discount.setAmount(new BigDecimal(10.0));
        discount.setType(Discount.Type.FIXED_DISCOUNT.getType());

        Integer id = discountDao.add(discount);
        discountDao.getSession().evict(discount);
        discount = discountDao.get(id);

        assertNotNull(discount);
        assertEquals(10, discount.getAmount().intValue());
        assertEquals("test2", discount.getDescription());
        assertEquals("₴", discount.getTypeAsString());
        assertEquals(Discount.Type.FIXED_DISCOUNT.getType(), (byte)discount.getType());
    }
}
