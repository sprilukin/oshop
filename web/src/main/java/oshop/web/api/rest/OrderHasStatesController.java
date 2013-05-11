package oshop.web.api.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import oshop.dao.GenericDao;
import oshop.model.OrderHasStates;
import oshop.web.converter.EntityConverter;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api/orderHasStates")
@Transactional(readOnly = true)
public class OrderHasStatesController extends BaseController<OrderHasStates, Integer> {

    private static final Log log = LogFactory.getLog(OrderHasStatesController.class);

    @Resource
    private GenericDao<OrderHasStates, Integer> orderHasStatesDao;

    @Resource(name = "orderHasStateToDTOConverter")
    private EntityConverter<OrderHasStates, Integer> converter;

    @Override
    protected GenericDao<OrderHasStates, Integer> getDao() {
        return orderHasStatesDao;
    }

    @Override
    protected EntityConverter<OrderHasStates, Integer> getToDTOConverter() {
        return converter;
    }
}
