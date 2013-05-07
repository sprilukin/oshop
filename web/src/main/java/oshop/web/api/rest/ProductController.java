package oshop.web.api.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import oshop.dao.GenericDao;
import oshop.model.Product;
import oshop.model.ProductCategory;
import oshop.web.converter.DefaultNoConverter;
import oshop.web.converter.EntityConverter;
import oshop.web.converter.ProductFromDTOConverter;
import oshop.web.converter.ProductToDTOConverter;

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
    protected EntityConverter<Product, Integer> getToDTOConverter() {
        return new ProductToDTOConverter();
    }

    @Override
    protected EntityConverter<Product, Integer> getFromDTOConverter() {
        return new DefaultNoConverter<Product, Integer>();
    }
}
