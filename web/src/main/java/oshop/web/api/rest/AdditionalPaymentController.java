package oshop.web.api.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import oshop.dao.GenericDao;
import oshop.model.AdditionalPayment;
import oshop.web.converter.EntityConverter;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api/additionalPayments")
@Transactional(readOnly = true)
public class AdditionalPaymentController extends BaseController<AdditionalPayment, Integer> {

    private static final Log log = LogFactory.getLog(AdditionalPaymentController.class);

    @Resource
    private GenericDao<AdditionalPayment, Integer> additionalPaymentDao;

    @Resource(name = "additionalPaymentToDTOConverter")
    private EntityConverter<AdditionalPayment, Integer> converter;

    @Override
    protected GenericDao<AdditionalPayment, Integer> getDao() {
        return additionalPaymentDao;
    }

    @Override
    protected EntityConverter<AdditionalPayment, Integer> getToDTOConverter() {
        return converter;
    }
}
