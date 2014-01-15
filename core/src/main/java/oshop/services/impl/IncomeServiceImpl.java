package oshop.services.impl;

import org.springframework.stereotype.Service;
import oshop.dao.GenericDao;
import oshop.model.Income;
import oshop.services.converter.EntityConverter;
import oshop.services.filter.Filter;

import javax.annotation.Resource;

@Service("incomeService")
public class IncomeServiceImpl extends GenericServiceImpl<Income, Integer> {

    @Resource
    private Filter dateBetweenFilter;

    @Resource
    protected GenericDao<Income, Integer> incomeDao;

    @Resource(name = "incomeToDTOConverter")
    private EntityConverter<Income, Integer> converter;

    @Override
    protected Filter getFilter() {
        return dateBetweenFilter;
    }

    @Override
    protected GenericDao<Income, Integer> getDao() {
        return incomeDao;
    }

    @Override
    protected EntityConverter<Income, Integer> getToDTOConverter() {
        return converter;
    }
}
