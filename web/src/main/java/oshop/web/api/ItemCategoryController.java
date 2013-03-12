package oshop.web.api;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/itemCategories")
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
    public ResponseEntity<?> addCategory(@RequestBody ItemCategory itemCategory){
        if (itemCategory.getName() != null) {
            Integer id = itemCategoryDao.add(itemCategory);
            return new ResponseEntity<ItemCategory>(itemCategoryDao.get(id), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<ErrorMessageResponse>(new ErrorMessageResponse("Category name can not be null"), HttpStatus.BAD_REQUEST);
        }
    }
}
