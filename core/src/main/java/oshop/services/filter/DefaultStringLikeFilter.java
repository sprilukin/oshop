package oshop.services.filter;

import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultStringLikeFilter extends BaseFilter {


    protected Criterion getRestrictionForFilter(String column, List<String> values, Criteria criteria) {
        Conjunction conjunction = Restrictions.conjunction();
        for (String likeExpression : values) {
            conjunction.add(Restrictions.like(column, likeExpression, MatchMode.ANYWHERE));
        }

        return conjunction;
    }
}
