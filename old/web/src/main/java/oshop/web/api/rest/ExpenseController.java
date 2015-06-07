package oshop.web.api.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import oshop.model.Expense;
import oshop.services.GenericService;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api/expenses")
public class ExpenseController extends BaseController<Expense, Integer> {

    private static final Log log = LogFactory.getLog(ExpenseController.class);

    @Resource
    private GenericService<Expense, Integer> expenseService;

    @Override
    protected GenericService<Expense, Integer> getService() {
        return expenseService;
    }
}
