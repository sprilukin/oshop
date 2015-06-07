package oshop.services.filter.comparison;

import org.hibernate.criterion.Criterion;
import oshop.services.filter.converter.Converter;

import java.util.List;

public interface Comparison {
    public <T> Criterion getComparison(String column, List<String> values, Converter<T> converter);
}
