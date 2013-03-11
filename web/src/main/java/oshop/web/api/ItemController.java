package oshop.web.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import oshop.dao.GenericDao;
import oshop.model.Item;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/api/item")
public class ItemController {

    @Resource
    private GenericDao<Item, Integer> itemDao;

    @RequestMapping(value="{name}", method = RequestMethod.GET)
    public @ResponseBody List<Item> listItems(@PathVariable String name) throws Exception {
        return itemDao.list(null, null);
    }
}
