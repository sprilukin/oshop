package oshop.services.impl;

import org.springframework.stereotype.Service;
import oshop.dao.GenericDao;
import oshop.model.City;
import oshop.services.converter.EntityConverter;

import javax.annotation.Resource;

@Service("cityService")
public class CityService extends GenericServiceImpl<City, Integer> {

    @Resource
    protected GenericDao<City, Integer> cityDao;

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
