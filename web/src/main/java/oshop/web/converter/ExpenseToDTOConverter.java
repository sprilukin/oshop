package oshop.web.converter;

import org.springframework.stereotype.Component;
import oshop.model.Discount;
import oshop.model.Expense;

@Component
public class ExpenseToDTOConverter extends BaseEntityConverter<Expense, Integer> {

    @Override
    protected Class<Expense> entityClass() {
        return Expense.class;
    }

    @Override
    protected void convert(Expense entity, Expense convertedEntity) throws Exception {
        convertedEntity.setDescription(entity.getDescription());
        convertedEntity.setAmount(entity.getAmount());
        convertedEntity.setDate(entity.getDate());
    }
}
