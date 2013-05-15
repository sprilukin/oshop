package oshop.web.api.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import oshop.dao.GenericDao;
import oshop.model.ShippingAddress;
import oshop.web.converter.EntityConverter;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api/shippingAddresses")
@Transactional(readOnly = true)
public class ShippingAddressController extends BaseController<ShippingAddress, Integer> {

    private static final Log log = LogFactory.getLog(ShippingAddressController.class);

    @Resource
    private GenericDao<ShippingAddress, Integer> shippingAddressDao;

    @Resource(name = "shippingAddressToDTOConverter")
    private EntityConverter<ShippingAddress, Integer> converter;

    @Override
    protected GenericDao<ShippingAddress, Integer> getDao() {
        return shippingAddressDao;
    }

    @Override
    protected EntityConverter<ShippingAddress, Integer> getToDTOConverter() {
        return converter;
    }
}
