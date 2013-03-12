package oshop.web.api;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import oshop.dao.GenericDao;
import oshop.model.ItemCategory;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api/itemCategory")
@Transactional
public class ItemCategoryController {

    private static final Log log = LogFactory.getLog(ItemCategoryController.class);

    @Resource
    private GenericDao<ItemCategory, Integer> itemCategoryDao;

    @RequestMapping(
            value="/add",
            method=RequestMethod.PUT,
            consumes={MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ItemCategory addUser(@RequestBody ItemCategory itemCategory){
        Integer id = itemCategoryDao.add(itemCategory);
        return itemCategoryDao.get(id);
    }

    @RequestMapping(value="/test", method=RequestMethod.POST)
    @ResponseBody
    public String addUser(@RequestBody String test){
        log.debug(test);
        return test;
    }
}
