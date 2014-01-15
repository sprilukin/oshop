package oshop.web.api.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import oshop.model.Income;
import oshop.services.GenericService;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api/incomes")
public class IncomeController extends BaseController<Income, Integer> {

    private static final Log log = LogFactory.getLog(IncomeController.class);

    @Resource
    private GenericService<Income, Integer> incomeService;

    @Override
    protected GenericService<Income, Integer> getService() {
        return incomeService;
    }
}
