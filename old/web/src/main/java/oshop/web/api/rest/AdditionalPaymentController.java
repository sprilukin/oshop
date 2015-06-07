package oshop.web.api.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import oshop.model.AdditionalPayment;
import oshop.services.GenericService;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api/additionalPayments")
public class AdditionalPaymentController extends BaseController<AdditionalPayment, Integer> {

    private static final Log log = LogFactory.getLog(AdditionalPaymentController.class);

    @Resource
    private GenericService<AdditionalPayment, Integer> additionalPaymentService;

    @Override
    protected GenericService<AdditionalPayment, Integer> getService() {
        return additionalPaymentService;
    }
}
