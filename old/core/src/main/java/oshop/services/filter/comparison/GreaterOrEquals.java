package oshop.services.filter.comparison;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import oshop.services.filter.converter.Converter;

import java.util.List;

public class GreaterOrEquals implements Comparison {

    @Override
    public <T> Criterion getComparison(String column, List<String> values, Converter<T> converter) {
        return Restrictions.ge(column, converter.convert(values.get(0)));
    }
}
