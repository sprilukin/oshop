package oshop.web.api.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import oshop.dao.GenericDao;
import oshop.model.Discount;
import oshop.web.converter.EntityConverter;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api/discounts")
@Transactional(readOnly = true)
public class DiscountController extends BaseController<Discount, Integer> {

    private static final Log log = LogFactory.getLog(DiscountController.class);

    @Resource
    private GenericDao<Discount, Integer> discountDao;

    @Resource(name = "discountToDTOConverter")
    private EntityConverter<Discount, Integer> converter;

    @Override
    protected GenericDao<Discount, Integer> getDao() {
        return discountDao;
    }

    @Override
    protected EntityConverter<Discount, Integer> getToDTOConverter() {
        return converter;
    }
}
