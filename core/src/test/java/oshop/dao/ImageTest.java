package oshop.dao;

import org.junit.Test;
import oshop.model.Image;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ImageTest extends BaseDaoTest {


    @Resource
    private GenericDao<Image, Integer> imageDao;

    @Test
    public void testSaleStateItems() throws Exception {
        setUpDb("oshop/dao/imageDataSet.xml");

        List<Image> images =  imageDao.list(null, null);
        assertEquals(1, images.size());

        assertEquals(6181, images.get(0).getData().length);
    }
}
