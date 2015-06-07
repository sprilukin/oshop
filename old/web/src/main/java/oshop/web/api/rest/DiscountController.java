package oshop.web.api.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import oshop.model.Discount;
import oshop.services.GenericService;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api/discounts")
public class DiscountController extends BaseController<Discount, Integer> {

    private static final Log log = LogFactory.getLog(DiscountController.class);

    @Resource
    private GenericService<Discount, Integer> discountService;

    @Override
    protected GenericService<Discount, Integer> getService() {
        return discountService;
    }
}
