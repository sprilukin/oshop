package oshop.web.api.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import oshop.dao.GenericDao;
import oshop.model.OrderHasProducts;
import oshop.web.converter.EntityConverter;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api/orderHasProducts")
@Transactional(readOnly = true)
public class OrderHasProductsController extends BaseController<OrderHasProducts, Integer> {

    private static final Log log = LogFactory.getLog(OrderHasProductsController.class);

    @Resource
    private GenericDao<OrderHasProducts, Integer> orderHasProductsDao;

    @Resource(name = "orderHasProductsToDTOConverter")
    private EntityConverter<OrderHasProducts, Integer> converter;

    @Override
    protected GenericDao<OrderHasProducts, Integer> getDao() {
        return orderHasProductsDao;
    }

    @Override
    protected EntityConverter<OrderHasProducts, Integer> getToDTOConverter() {
        return converter;
    }
}
