package oshop.services.filter.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import oshop.services.filter.ComparisonBasedFilter;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class OrdersFilter extends ComparisonBasedFilter {

    @Resource
    private Map<String, Class> ordersFieldTypesMapping;

    @Resource
    private Map<String, String> ordersAliasesMapping;

    @Override
    protected Map<String, Class> getFieldsMapping() {
        return ordersFieldTypesMapping;
    }

    @Override
    protected Map<String, String> getAliasesMapping() {
        return ordersAliasesMapping;
    }

    @Override
    protected Criterion getCustomRestrictionForFilter(String column, List<String> values, Criteria criteria) {
        if (column.equals("orderStateNotIn")) {
            return orderStateNotIn(values, criteria);
        }

        return null;
    }

    private Criterion orderStateNotIn(List<String> values, Criteria criteria) {
        return Restrictions.or(
                Restrictions.isNull("currentOrderStateName"),
                Restrictions.not(Restrictions.in("currentOrderStateName", values))
        );
    }
}
