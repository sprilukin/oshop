package oshop.web.converter.order;

import org.springframework.stereotype.Component;
import oshop.model.AdditionalPayment;
import oshop.web.converter.BaseEntityConverter;

@Component
public class AdditionalPaymentToDTOConverter extends BaseEntityConverter<AdditionalPayment, Integer> {

    @Override
    protected Class<AdditionalPayment> entityClass() {
        return AdditionalPayment.class;
    }

    @Override
    protected void convert(AdditionalPayment entity, AdditionalPayment convertedEntity) throws Exception {
        convertedEntity.setDescription(entity.getDescription());
        convertedEntity.setAmount(entity.getAmount());
    }
}
