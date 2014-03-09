package oshop.services.filter.converter;

import java.util.List;

public interface Converter<T> {
    public T convert(String value);
    public List<T> convert(List<String> values);
}
