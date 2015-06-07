package oshop.services.filter;

import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.sql.JoinType;

import java.util.Iterator;
import java.util.List;

public class FilterUtils {

    public static boolean aliasExists(Criteria criteria, String path, String alias) {
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

    public static Criteria addAlias(Criteria criteria, String path, String alias, JoinType joinType) {
        if (!aliasExists(criteria, path, alias)) {
            return criteria.createAlias(path, alias, joinType);
        }

        return criteria;
    }

    public static Criteria addAlias(Criteria criteria, String path, String alias) {
        return addAlias(criteria, path, alias, JoinType.INNER_JOIN);
    }

    public static Criterion createDisjunction(CriterionFactory factory, List<String> values) {
        Disjunction disjunction = Restrictions.disjunction();
        return appendToJunction(factory, disjunction, values);
    }

    public static Criterion createConjunction(CriterionFactory factory, List<String> values) {
        Conjunction conjunction = Restrictions.conjunction();
        return appendToJunction(factory, conjunction, values);
    }

    private static Criterion appendToJunction(CriterionFactory factory, Junction junction, List<String> values) {
        for (String value : values) {
            junction.add(factory.createCriterion(value));
        }

        return junction;
    }

    public static interface CriterionFactory {
        public Criterion createCriterion(String value);
    }
}
