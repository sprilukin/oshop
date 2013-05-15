package oshop.web.api.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import oshop.dao.GenericDao;
import oshop.model.ShippingType;
import oshop.web.converter.EntityConverter;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api/shippingTypes")
@Transactional(readOnly = true)
public class ShippingTypeController extends BaseController<ShippingType, Integer> {

    private static final Log log = LogFactory.getLog(ShippingTypeController.class);

    @Resource
    private GenericDao<ShippingType, Integer> shippingTypeDao;

    @Resource(name = "shippingTypeToDTOConverter")
    private EntityConverter<ShippingType, Integer> converter;

    @Override
    protected GenericDao<ShippingType, Integer> getDao() {
        return shippingTypeDao;
    }

    @Override
    protected EntityConverter<ShippingType, Integer> getToDTOConverter() {
        return converter;
    }
}
