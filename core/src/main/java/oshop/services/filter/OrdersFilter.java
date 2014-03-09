package oshop.services.filter;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class OrdersFilter extends ComparisonBasedFilter {

    @Resource
    private Map<String, Class> ordersFieldsMapping;

    @Resource
    private Map<String, String> ordersAliasesMapping;

    @Override
    protected Map<String, Class> getFieldsMapping() {
        return ordersFieldsMapping;
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
