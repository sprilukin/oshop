package oshop.services.filter.comparison;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import oshop.services.filter.converter.Converter;

import java.util.List;

public class In implements Comparison {

    @Override
    public <T> Criterion getComparison(String column, List<String> values, Converter<T> converter) {
        return Restrictions.in(column, converter.convert(values));
    }
}
