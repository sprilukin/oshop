package oshop.web.api.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import oshop.dao.GenericDao;
import oshop.model.City;
import oshop.web.converter.EntityConverter;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api/cities")
@Transactional(readOnly = true)
public class CityController extends BaseController<City, Integer> {

    private static final Log log = LogFactory.getLog(CityController.class);

    @Resource
    private GenericDao<City, Integer> cityDao;

    @Resource(name = "cityToDTOConverter")
    private EntityConverter<City, Integer> converter;

    @Override
    protected GenericDao<City, Integer> getDao() {
        return cityDao;
    }

    @Override
    protected EntityConverter<City, Integer> getToDTOConverter() {
        return converter;
    }
}
