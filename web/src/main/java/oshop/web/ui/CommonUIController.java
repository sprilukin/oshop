package oshop.web.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "")
public class CommonUIController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() throws Exception {
        return "redirect:productCategories";
    }

    @RequestMapping(value = "/itemCategories", method = RequestMethod.GET)
    public String itemCategories() throws Exception {
        return "itemCategories";
    }

    @RequestMapping(value = "/productCategories", method = RequestMethod.GET)
    public String productCategories() throws Exception {
        return "productCategories";
    }

    @RequestMapping(value = "/productCategories/{id}/products", method = RequestMethod.GET)
    public String productsByCategory(@PathVariable Integer id, Model model) throws Exception {
        model.addAttribute("productCategoryId", id);
        return "products";
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public String products() throws Exception {
        return "products";
    }
}
