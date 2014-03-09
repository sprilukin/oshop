package oshop.services.filter.converter;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseConverter<T> implements Converter<T> {

    @Override
    public List<T> convert(List<String> values) {
        List<T> list = new ArrayList<T>();

        for (String value : values) {
            list.add(convert(value));
        }

        return list;
    }
}
