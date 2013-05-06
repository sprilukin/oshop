package oshop.web.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "")
public class CommonUIController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() throws Exception {
        return "redirect:itemCategories";
    }

    @RequestMapping(value = "/itemCategories", method = RequestMethod.GET)
    public String itemCategories() throws Exception {
        return "itemCategories";
    }

    @RequestMapping(value = "/productCategories", method = RequestMethod.GET)
    public String productCategories() throws Exception {
        return "productCategories";
    }
}
