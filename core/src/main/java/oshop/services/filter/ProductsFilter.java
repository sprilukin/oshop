package oshop.services.filter;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class ProductsFilter extends ComparisonBasedFilter {

    @Resource
    private Map<String, Class> productsFieldsMapping;

    @Resource
    private Map<String, String> productsAliasesMapping;


    @Override
    protected Map<String, Class> getFieldsMapping() {
        return productsFieldsMapping;
    }

    @Override
    protected Map<String, String> getAliasesMapping() {
        return productsAliasesMapping;
    }

    @Override
    protected Criterion getCustomRestrictionForFilter(String column, List<String> values, Criteria criteria) {
        if (column.equals("orderStateIn")) {
            return orderStateIn(values, criteria);
        }

        return null;
    }

    private Criterion orderStateIn(List<String> values, Criteria criteria) {
        FilterUtils.addAlias(criteria, "orders", "o", JoinType.LEFT_OUTER_JOIN);

        return Restrictions.or(
                Restrictions.isEmpty("orders"),
                Restrictions.in("o.currentOrderStateName", values)
        );
    }
}
