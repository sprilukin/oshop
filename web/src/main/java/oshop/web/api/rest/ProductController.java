package oshop.web.api.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import oshop.model.Product;
import oshop.services.GenericService;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api/products")
public class ProductController extends BaseController<Product, Integer> {

    private static final Log log = LogFactory.getLog(ProductController.class);

    @Resource
    private GenericService<Product, Integer> productService;

    @Override
    protected GenericService<Product, Integer> getService() {
        return productService;
    }
}
