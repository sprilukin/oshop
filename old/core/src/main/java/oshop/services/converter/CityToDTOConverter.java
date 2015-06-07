package oshop.services.converter;

import org.springframework.stereotype.Component;
import oshop.model.City;

@Component
public class CityToDTOConverter extends BaseEntityConverter<City, Integer> {

    @Override
    protected Class<City> entityClass() {
        return City.class;
    }

    @Override
    protected void convert(City entity, City convertedEntity) throws Exception {
        convertedEntity.setName(entity.getName());
        convertedEntity.setRegion(entity.getRegion());
        convertedEntity.setLatitude(entity.getLatitude());
        convertedEntity.setLongitude(entity.getLongitude());
    }
}
