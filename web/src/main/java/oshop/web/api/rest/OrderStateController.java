package oshop.web.api.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import oshop.model.OrderState;
import oshop.services.GenericService;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api/orderStates")
public class OrderStateController extends BaseController<OrderState, Integer> {

    private static final Log log = LogFactory.getLog(OrderStateController.class);

    @Resource
    private GenericService<OrderState, Integer> orderStateService;

    @Override
    protected GenericService<OrderState, Integer> getService() {
        return orderStateService;
    }
}
