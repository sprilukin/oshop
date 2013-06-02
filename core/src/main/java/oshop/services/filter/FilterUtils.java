package oshop.services.filter;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;

import java.util.ArrayList;
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

    public static Criterion stringLikeDisjunction(String column, List<String> values) {
        Disjunction disjunction = Restrictions.disjunction();
        for (String likeExpression : values) {
            disjunction.add(Restrictions.like(column, likeExpression, MatchMode.ANYWHERE));
        }

        return disjunction;
    }

    public static List<Integer> convertStringListToIntegerList(List<String> list) {
        List<Integer> integerList = new ArrayList<Integer>(list.size());

        for (String value: list) {
            integerList.add(Integer.parseInt(value));
        }

        return integerList;
    }
}
