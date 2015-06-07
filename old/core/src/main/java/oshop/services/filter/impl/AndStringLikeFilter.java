package oshop.services.filter.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Component;
import oshop.services.filter.BaseFilter;
import oshop.services.filter.CriterionFactory;

import javax.annotation.Resource;
import java.util.List;

@Component
public class AndStringLikeFilter extends BaseFilter {

    @Resource
    private CriterionFactory criterionFactory;

    protected Criterion getRestrictionForFilter(final String column, List<String> values, Criteria criteria) {
        return criterionFactory.getCriterion(column, values, "LIKE", String.class);
    }
}
