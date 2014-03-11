package oshop.services.filter;

import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.Map;

public abstract class BaseFilter implements Filter {


    public void applyFilters(Map<String, List<String>> filters, Criteria criteria) {
        Conjunction conjunction = Restrictions.conjunction();
        for (Map.Entry<String, List<String>> entry : filters.entrySet()) {
            Criterion criterion = getRestrictionForFilter(entry.getKey(), entry.getValue(), criteria);
            if (criterion != null) {
                conjunction.add(criterion);
            }
        }

        criteria.add(conjunction);
    }

    protected abstract Criterion getRestrictionForFilter(String column, List<String> values, Criteria criteria);
}
