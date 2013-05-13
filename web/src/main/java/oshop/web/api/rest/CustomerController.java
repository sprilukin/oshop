package oshop.web.api.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import oshop.dao.GenericDao;
import oshop.model.Customer;
import oshop.web.converter.EntityConverter;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api/customers")
@Transactional(readOnly = true)
public class CustomerController extends BaseController<Customer, Integer> {

    private static final Log log = LogFactory.getLog(CustomerController.class);

    @Resource
    private GenericDao<Customer, Integer> customerDao;

    @Resource(name = "customerToDTOConverter")
    private EntityConverter<Customer, Integer> converter;

    @Override
    protected GenericDao<Customer, Integer> getDao() {
        return customerDao;
    }

    @Override
    protected EntityConverter<Customer, Integer> getToDTOConverter() {
        return converter;
    }
}
