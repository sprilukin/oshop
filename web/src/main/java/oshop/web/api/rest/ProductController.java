package oshop.web.api.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import oshop.dao.GenericDao;
import oshop.model.Product;
import oshop.web.converter.EntityDetachConverter;
import oshop.web.converter.ProductConverter;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api/products")
@Transactional(readOnly = true)
public class ProductController extends BaseController<Product, Integer> {

    private static final Log log = LogFactory.getLog(ProductController.class);

    @Resource
    private GenericDao<Product, Integer> productDao;

    @Override
    protected GenericDao<Product, Integer> getDao() {
        return productDao;
    }

    @Override
    protected EntityDetachConverter<Product, Integer> getDefaultConverter() {
        return new ProductConverter();
    }
}
