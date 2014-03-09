package oshop.services.filter.comparison;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import oshop.services.filter.converter.Converter;

import java.util.List;

public class Beetween implements Comparison {

    @Override
    public <T> Criterion getComparison(String column, List<String> values, Converter<T> converter) {
        return Restrictions.between(column, converter.convert(values.get(0)), converter.convert(values.get(1)));
    }
}
