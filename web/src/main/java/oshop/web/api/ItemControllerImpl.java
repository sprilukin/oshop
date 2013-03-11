package oshop.web.api;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import oshop.dao.GenericDao;
import oshop.model.Item;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Transactional
@Controller
@RequestMapping("/api/item")
public class ItemControllerImpl {

    @Resource
    private GenericDao<Item, Integer> itemDao;

    @RequestMapping(value="/list", method = RequestMethod.GET)
    public @ResponseBody List<Item> listItems(
            @RequestParam(value = "category", required = false) Integer categoryId,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit) throws Exception {

        Item item = new Item();
        item.setName("ffff");
        item.setPrice(new BigDecimal(10.0));
        itemDao.add(item);

        Criteria criteria = itemDao.createCriteria();
        if (categoryId != null) {
            criteria.add(Restrictions.eq("category.id", categoryId));
        }

        return itemDao.list(criteria, page, limit);
    }
}
