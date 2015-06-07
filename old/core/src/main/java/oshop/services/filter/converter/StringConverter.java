package oshop.services.filter.converter;

import java.util.List;

public class StringConverter implements Converter<String> {

    @Override
    public String convert(String value) {
        return value;
    }

    @Override
    public List<String> convert(List<String> values) {
        return values;
    }
}
