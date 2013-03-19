package oshop.web.ui;

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
@RequestMapping(value = "/itemCategories")
public class ItemCategoriesController {

    @RequestMapping(method = RequestMethod.GET)
    public String main() throws Exception {
        return "itemCategories";
    }
}
