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
    public String productsByCategory(@PathVariable Integer id) throws Exception {
        return "products";
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public String products() throws Exception {
        return "products";
    }

    @RequestMapping(value = "/customers/{id}/orders/add", method = RequestMethod.GET)
    public String addOrderByCustomer(@PathVariable Integer id) throws Exception {
        return "orderEdit";
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public String orders() throws Exception {
        return "ordersList";
    }

    @RequestMapping(value = "/orders/{id}", method = RequestMethod.GET)
    public String editOrder(@PathVariable Integer id) throws Exception {
        return "orderEdit";
    }

    @RequestMapping(value = "/orders/add", method = RequestMethod.GET)
    public String addOrder() throws Exception {
        return "orderEdit";
    }
}
