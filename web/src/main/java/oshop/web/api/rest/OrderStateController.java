package oshop.web.api.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import oshop.dao.GenericDao;
import oshop.model.OrderState;
import oshop.web.converter.EntityConverter;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api/orderStates")
@Transactional(readOnly = true)
public class OrderStateController extends BaseController<OrderState, Integer> {

    private static final Log log = LogFactory.getLog(OrderStateController.class);

    @Resource
    private GenericDao<OrderState, Integer> orderStateDao;

    @Resource(name = "defaultConverter")
    private EntityConverter<OrderState, Integer> converter;

    @Override
    protected GenericDao<OrderState, Integer> getDao() {
        return orderStateDao;
    }

    @Override
    protected EntityConverter<OrderState, Integer> getToDTOConverter() {
        return converter;
    }
}
