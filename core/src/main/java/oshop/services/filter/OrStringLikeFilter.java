package oshop.services.filter;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrStringLikeFilter extends BaseFilter {


    protected Criterion getRestrictionForFilter(String column, List<String> values, Criteria criteria) {
        return FilterUtils.stringLikeDisjunction(column, values);
    }
}
