package oshop.web.api;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import oshop.dao.GenericDao;
import oshop.model.Item;
import oshop.model.ItemCategory;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/api/itemCategories")
@Transactional(readOnly = true)
public class ItemCategoryController {

    private static final Log log = LogFactory.getLog(ItemCategoryController.class);

    @Resource
    private GenericDao<ItemCategory, Integer> itemCategoryDao;

    @Resource
    private GenericDao<Item, Integer> itemDao;

    @RequestMapping(
            value = "/add",
            method = RequestMethod.PUT,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Transactional(readOnly = false)
    public ItemCategory add(@RequestBody @Valid ItemCategory itemCategory) {
        Integer id = itemCategoryDao.add(itemCategory);
        return itemCategoryDao.get(id);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ItemCategory findById(@PathVariable Integer id) {
        return itemCategoryDao.get(id);
    }

    @RequestMapping(
            value = "/",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<ItemCategory> list(
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "name", required = false) String name) {

        Criteria criteria = itemCategoryDao.createCriteria();
        if (name != null) {
            criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        }

        return itemCategoryDao.list(criteria, offset, limit);
    }

    @RequestMapping(
            value = "/{id}/items",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Item> items(
            @PathVariable Integer id,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "price", required = false) BigDecimal price) {

        Criteria criteria = itemDao.createCriteria();
        criteria.createAlias("category", "c").add(Restrictions.eq("c.id", id));
        if (name != null) {
            criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        }
        if (price != null) {
            criteria.add(Restrictions.eq("price", price));
        }

        return itemDao.list(criteria, offset, limit);
    }
}
