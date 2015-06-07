package oshop.web.api.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import oshop.model.City;
import oshop.services.GenericService;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api/cities")
public class CityController extends BaseController<City, Integer> {

    private static final Log log = LogFactory.getLog(CityController.class);

    @Resource
    private GenericService<City, Integer> cityService;

    @Override
    protected GenericService<City, Integer> getService() {
        return cityService;
    }
}
