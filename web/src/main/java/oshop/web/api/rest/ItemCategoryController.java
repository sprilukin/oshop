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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import oshop.dao.GenericDao;
import oshop.model.Item;
import oshop.model.ItemCategory;
import oshop.web.converter.DefaultNoConverter;
import oshop.web.converter.EntityConverter;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/itemCategories")
@Transactional(readOnly = true)
public class ItemCategoryController extends BaseController<ItemCategory, Integer> {

    private static final Log log = LogFactory.getLog(ItemCategoryController.class);

    @Resource
    private GenericDao<ItemCategory, Integer> itemCategoryDao;

    @Resource
    private GenericDao<Item, Integer> itemDao;

    @Override
    protected GenericDao<ItemCategory, Integer> getDao() {
        return itemCategoryDao;
    }

    @Override
    protected EntityConverter<ItemCategory, Integer> getToDTOConverter() {
        return new DefaultNoConverter<ItemCategory, Integer>();
    }

    @Override
    protected EntityConverter<ItemCategory, Integer> getFromDTOConverter() {
        return new DefaultNoConverter<ItemCategory, Integer>();
    }

    @RequestMapping(
            value = "/{id}/items",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    //TODO: use ResponseEntity<?>
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
    //TODO: use ResponseEntity<?>
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
