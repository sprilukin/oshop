package oshop.services.impl;

import org.springframework.stereotype.Service;
import oshop.dao.GenericDao;
import oshop.model.Expense;
import oshop.services.converter.EntityConverter;
import oshop.services.filter.Filter;

import javax.annotation.Resource;

@Service("expenseService")
public class ExpenseServiceImpl extends GenericServiceImpl<Expense, Integer> {

    @Resource
    private Filter dateBetweenFilter;

    @Resource
    protected GenericDao<Expense, Integer> expenseDao;

    @Resource(name = "expenseToDTOConverter")
    private EntityConverter<Expense, Integer> converter;

    @Override
    protected Filter getFilter() {
        return dateBetweenFilter;
    }

    @Override
    protected GenericDao<Expense, Integer> getDao() {
        return expenseDao;
    }

    @Override
    protected EntityConverter<Expense, Integer> getToDTOConverter() {
        return converter;
    }
}
