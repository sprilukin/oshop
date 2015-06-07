package oshop.services.impl;

import org.springframework.stereotype.Service;
import oshop.dao.GenericDao;
import oshop.model.AdditionalPayment;
import oshop.services.converter.EntityConverter;

import javax.annotation.Resource;

@Service("additionalPaymentService")
public class AdditionalPaymentServiceImpl extends GenericServiceImpl<AdditionalPayment, Integer> {

    @Resource
    protected GenericDao<AdditionalPayment, Integer> additionalPaymentDao;

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
