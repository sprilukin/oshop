package oshop.services.filter;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Component;

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
