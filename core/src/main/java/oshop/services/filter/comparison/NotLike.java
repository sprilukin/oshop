package oshop.services.filter.comparison;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import oshop.services.filter.FilterUtils;
import oshop.services.filter.converter.Converter;

import java.util.List;

public class NotLike implements Comparison {

    @Override
    public <T> Criterion getComparison(final String column, List<String> values, final Converter<T> converter) {
        return FilterUtils.createConjunction(new FilterUtils.CriterionFactory() {
            @Override
            public Criterion createCriterion(String value) {
                return Restrictions.not(Restrictions.like(column, value, MatchMode.ANYWHERE));
            }
        }, values);
    }
}
