package oshop.web.api.rest.filter;

import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;

import java.util.Iterator;
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

    protected Criteria addAlias(Criteria criteria, String path, String alias) {
        if (!aliasExists(criteria, path, alias)) {
            return criteria.createAlias(path, alias);
        }

        return criteria;
    }

    private boolean aliasExists(Criteria criteria, String path, String alias) {
        if (criteria == null) {
            throw new IllegalArgumentException("Unexpected subcriteria without existing parent criteria");
        }

        if (criteria instanceof CriteriaImpl) {
            Iterator i = ((CriteriaImpl) criteria).iterateSubcriteria();
            while (i.hasNext()) {
                Criteria nextCriteria = (Criteria)i.next();
                if (!(nextCriteria instanceof CriteriaImpl.Subcriteria)) {
                    throw new IllegalArgumentException("Unexpected type of criteria");
                }

                CriteriaImpl.Subcriteria subcriteria = (CriteriaImpl.Subcriteria) nextCriteria;
                if (subcriteria.getPath().equals(path) && subcriteria.getAlias().equals(alias)) {
                    return true;
                } else if (subcriteria.getPath().equals(path) || subcriteria.getAlias().equals(alias)) {
                    throw new IllegalArgumentException("Attempt to create alias for same path but with different name");
                }
            }

            return false;
        } else if (criteria instanceof CriteriaImpl.Subcriteria) {
            Criteria parent = ((CriteriaImpl.Subcriteria) criteria).getParent();
            return aliasExists(parent, path, alias);
        } else {
            throw new IllegalArgumentException("Unexpected type of criteria");
        }
    }

    protected abstract Criterion getRestrictionForFilter(String column, List<String> values, Criteria criteria);
}
