package oshop.services.filter.converter;

import java.util.Date;

public class DateConverter extends BaseConverter<Date> {

    @Override
    public Date convert(String value) {
        return new Date(Long.parseLong(value));
    }
}
