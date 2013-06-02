package oshop.web.api.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import oshop.model.OrderHasOrderStates;
import oshop.services.GenericService;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api/orderHasOrderStates")
public class OrderHasOrderStatesController extends BaseController<OrderHasOrderStates, Integer> {

    private static final Log log = LogFactory.getLog(OrderHasOrderStatesController.class);

    @Resource
    private GenericService<OrderHasOrderStates, Integer> orderHasOrderStatesService;

    @Override
    protected GenericService<OrderHasOrderStates, Integer> getService() {
        return orderHasOrderStatesService;
    }
}
