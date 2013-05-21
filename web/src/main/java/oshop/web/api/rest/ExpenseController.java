package oshop.web.api.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import oshop.dao.GenericDao;
import oshop.model.Expense;
import oshop.web.converter.EntityConverter;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api/expenses")
@Transactional(readOnly = true)
public class ExpenseController extends BaseController<Expense, Integer> {

    private static final Log log = LogFactory.getLog(ExpenseController.class);

    @Resource
    private GenericDao<Expense, Integer> expenseDao;

    @Resource(name = "expenseToDTOConverter")
    private EntityConverter<Expense, Integer> converter;

    @Override
    protected GenericDao<Expense, Integer> getDao() {
        return expenseDao;
    }

    @Override
    protected EntityConverter<Expense, Integer> getToDTOConverter() {
        return converter;
    }
}
