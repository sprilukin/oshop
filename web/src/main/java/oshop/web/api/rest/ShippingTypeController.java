package oshop.web.api.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import oshop.model.ShippingType;
import oshop.services.GenericService;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api/shippingTypes")
public class ShippingTypeController extends BaseController<ShippingType, Integer> {

    private static final Log log = LogFactory.getLog(ShippingTypeController.class);

    @Resource
    private GenericService<ShippingType, Integer> shippingTypeService;

    @Override
    protected GenericService<ShippingType, Integer> getService() {
        return shippingTypeService;
    }
}
