package oshop.web.api.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.MatrixVariable;
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
import java.util.List;
import java.util.Map;

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
            method = RequestMethod.DELETE,
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    @Transactional(readOnly = false)
    public void delete(@PathVariable Integer id) {
        itemCategoryDao.remove(id);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ItemCategory get(@PathVariable Integer id) {
        return itemCategoryDao.get(id);
    }

    @RequestMapping(
            value = "/",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<ItemCategory> list(
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "offset", required = false) Integer offset) {

        Criteria criteria = itemCategoryDao.createCriteria();
        return itemCategoryDao.list(criteria, offset, limit);
    }

    @RequestMapping(
            value = "/{filter}/{sort}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<ItemCategory> listWithFiltersAndSorters(
            @MatrixVariable(pathVar="filter", required = false) Map<String, List<String>> filters,
            @MatrixVariable(pathVar="sort", required = false) Map<String, List<String>> sorters,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "offset", required = false) Integer offset) {

        Criteria criteria = itemCategoryDao.createCriteria();

        ControllerUtils.applyFilters(filters, criteria);
        ControllerUtils.applySorters(sorters, criteria);

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
            @RequestParam(value = "offset", required = false) Integer offset) {

        Criteria criteria = itemDao.createCriteria();
        criteria.createAlias("category", "c").add(Restrictions.eq("c.id", id));
        return itemDao.list(criteria, offset, limit);
    }

    @RequestMapping(
            value = "/{id}/items/{filter}/{sort}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Item> itemsWithFiltersAndSorters(
            @PathVariable Integer id,
            @MatrixVariable(pathVar="filter", required = false) Map<String, List<String>> filters,
            @MatrixVariable(pathVar="sort", required = false) Map<String, List<String>> sorters,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "offset", required = false) Integer offset) {

        Criteria criteria = itemDao.createCriteria();
        criteria.createAlias("category", "c").add(Restrictions.eq("c.id", id));

        ControllerUtils.applyFilters(filters, criteria);
        ControllerUtils.applySorters(sorters, criteria);

        return itemDao.list(criteria, offset, limit);
    }
}
