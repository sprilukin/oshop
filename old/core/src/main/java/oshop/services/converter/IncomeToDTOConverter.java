package oshop.services.converter;

import org.springframework.stereotype.Component;
import oshop.model.Income;

@Component
public class IncomeToDTOConverter extends BaseEntityConverter<Income, Integer> {

    @Override
    protected Class<Income> entityClass() {
        return Income.class;
    }

    @Override
    protected void convert(Income entity, Income convertedEntity) throws Exception {
        convertedEntity.setDescription(entity.getDescription());
        convertedEntity.setAmount(entity.getAmount());
        convertedEntity.setDate(entity.getDate());
    }
}
