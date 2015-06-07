package oshop.web.api.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import oshop.model.ShippingAddress;
import oshop.services.GenericService;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api/shippingAddresses")
public class ShippingAddressController extends BaseController<ShippingAddress, Integer> {

    private static final Log log = LogFactory.getLog(ShippingAddressController.class);

    @Resource
    private GenericService<ShippingAddress, Integer> shippingAddressService;

    @Override
    protected GenericService<ShippingAddress, Integer> getService() {
        return shippingAddressService;
    }
}
