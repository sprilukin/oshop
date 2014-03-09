package oshop.services.filter;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrStringLikeFilter extends BaseFilter {


    protected Criterion getRestrictionForFilter(final String column, List<String> values, Criteria criteria) {
        return FilterUtils.createDisjunction(new FilterUtils.CriterionFactory() {
            @Override
            public Criterion createCriterion(String value) {
                return Restrictions.like(column, value, MatchMode.ANYWHERE);
            }
        }, values);
    }
}
