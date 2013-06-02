package oshop.services.filter;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrStringLikeFilter extends BaseFilter {


    protected Criterion getRestrictionForFilter(String column, List<String> values, Criteria criteria) {
        Disjunction disjunction = Restrictions.disjunction();
        for (String likeExpression : values) {
            disjunction.add(Restrictions.like(column, likeExpression, MatchMode.ANYWHERE));
        }

        return disjunction;
    }
}
