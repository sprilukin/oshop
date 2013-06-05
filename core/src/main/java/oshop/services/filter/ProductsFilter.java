package oshop.services.filter;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductsFilter extends BaseFilter {

    static enum ProductsFilterNames {
        idIn("id"),
        nameLike("name"),
        descriptionLike("description"),
        categoryLike("category"),
        orderStateIn("orderStateIn");

        private String name;

        ProductsFilterNames(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }

        static ProductsFilterNames fromName(String name) {
            for (ProductsFilterNames filter: ProductsFilterNames.values()) {
                if (filter.getName().equals(name)) {
                    return filter;
                }
            }

            throw new IllegalArgumentException();
        }
    }

    @Override
    protected Criterion getRestrictionForFilter(String column, List<String> values, Criteria criteria) {

        switch (ProductsFilterNames.fromName(column)) {
            case idIn:
                return idIn(values, criteria);
            case nameLike:
                return nameLike(values, criteria);
            case descriptionLike:
                return descriptionLike(values, criteria);
            case categoryLike:
                return categoryLike(values, criteria);
            case orderStateIn:
                return orderStateIn(values, criteria);
            default:
                throw new IllegalArgumentException("Invalid filter");
        }
    }

    private Criterion idIn(List<String> values, Criteria criteria) {
        List<Integer> ids = FilterUtils.convertStringListToIntegerList(values);

        return Restrictions.in("id", ids);
    }

    private Criterion nameLike(List<String> values, Criteria criteria) {
        return FilterUtils.stringLikeDisjunction("name", values);
    }

    private Criterion descriptionLike(List<String> values, Criteria criteria) {
        return FilterUtils.stringLikeDisjunction("description", values);
    }

    private Criterion categoryLike(List<String> values, Criteria criteria) {
        addAlias(criteria, "category", "c");
        return FilterUtils.stringLikeDisjunction("c.name", values);
    }

    private Criterion orderStateIn(List<String> values, Criteria criteria) {
        addAlias(criteria, "orders", "o", JoinType.LEFT_OUTER_JOIN);

        return Restrictions.or(
                Restrictions.isEmpty("orders"),
                Restrictions.in("o.currentOrderStateName", values)
        );
    }
}
