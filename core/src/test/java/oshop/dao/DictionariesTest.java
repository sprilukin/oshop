package oshop.dao;

import org.junit.Test;
import oshop.model.DiscountType;
import oshop.model.SaleState;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DictionariesTest extends BaseDaoTest {


    @Resource
    private GenericDao<SaleState, Integer> saleStateDao;

    @Resource
    private GenericDao<DiscountType, Integer> discountTypeDao;

    @Test
    public void testSaleStateItems() throws Exception {
        setUpDb("oshop/dao/saleState.xml");

        List<SaleState> saleStateList =  saleStateDao.list(null, null);
        assertEquals(6, saleStateList.size());
    }

    @Test
    public void testDiscountTypeItems() throws Exception {
        setUpDb("oshop/dao/discountType.xml");

        List<DiscountType> discountTypeList =  discountTypeDao.list(null, null);
        assertEquals(3, discountTypeList.size());
    }
}
