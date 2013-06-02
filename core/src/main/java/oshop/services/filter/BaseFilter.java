package oshop.services.filter;

import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import java.util.List;
import java.util.Map;

public abstract class BaseFilter implements Filter {


    public void applyFilters(Map<String, List<String>> filters, Criteria criteria) {
        Conjunction conjunction = Restrictions.conjunction();
        for (Map.Entry<String, List<String>> entry : filters.entrySet()) {
            conjunction.add(getRestrictionForFilter(entry.getKey(), entry.getValue(), criteria));
        }

        criteria.add(conjunction);
    }

    protected Criteria addAlias(Criteria criteria, String path, String alias, JoinType joinType) {
        if (!FilterUtils.aliasExists(criteria, path, alias)) {
            return criteria.createAlias(path, alias, joinType);
        }

        return criteria;
    }

    protected Criteria addAlias(Criteria criteria, String path, String alias) {
        return addAlias(criteria, path, alias, JoinType.INNER_JOIN);
    }

    protected abstract Criterion getRestrictionForFilter(String column, List<String> values, Criteria criteria);
}
