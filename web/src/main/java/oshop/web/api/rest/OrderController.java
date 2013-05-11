package oshop.web.api.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import oshop.dao.GenericDao;
import oshop.model.Order;
import oshop.web.converter.EntityConverter;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api/orders")
@Transactional(readOnly = true)
public class OrderController extends BaseController<Order, Integer> {

    private static final Log log = LogFactory.getLog(OrderController.class);

    @Resource
    private GenericDao<Order, Integer> orderDao;

    @Resource(name = "orderToDTOConverter")
    private EntityConverter<Order, Integer> converter;

    @Override
    protected GenericDao<Order, Integer> getDao() {
        return orderDao;
    }

    @Override
    protected EntityConverter<Order, Integer> getToDTOConverter() {
        return converter;
    }
}
