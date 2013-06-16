package oshop.services.filter;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Component;
import oshop.model.EntityUtils;

import java.math.BigDecimal;
import java.util.List;

@Component
public class ProductsFilter extends BaseFilter {

    static enum ProductsFilterNames {
        idEq("idEQ"),
        idNeq("idNEQ"),
        idGe("idGE"),
        idLe("idLE"),
        idGt("idGT"),
        idLt("idLT"),
        idBtwn("idBTWN"),
        nameLike("nameLIKE"),
        nameNlike("nameNLIKE"),
        descriptionLike("descriptionLIKE"),
        descriptionNlike("descriptionNLIKE"),
        categoryLike("categoryLIKE"),
        categoryNlike("categoryNLIKE"),
        priceEq("priceEQ"),
        priceNeq("priceNEQ"),
        priceGe("priceGE"),
        priceLe("priceLE"),
        priceGt("priceGT"),
        priceLt("priceLT"),
        priceBtwn("priceBTWN"),
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
            case idEq:
                return idEquals(values, criteria);
            case idNeq:
                return idNotEquals(values, criteria);
            case idGe:
                return idGreaterOrEquals(values, criteria);
            case idLe:
                return idLessOrEquals(values, criteria);
            case idGt:
                return idGreater(values, criteria);
            case idLt:
                return idLess(values, criteria);
            case idBtwn:
                return idBetween(values, criteria);
            case nameLike:
                return nameLike(values, criteria);
            case nameNlike:
                return nameNotLike(values, criteria);
            case descriptionLike:
                return descriptionLike(values, criteria);
            case descriptionNlike:
                return descriptionNotLike(values, criteria);
            case categoryLike:
                return categoryLike(values, criteria);
            case categoryNlike:
                return categoryNotLike(values, criteria);
            case priceEq:
                return priceEquals(values, criteria);
            case priceNeq:
                return priceNotEquals(values, criteria);
            case priceGe:
                return priceGreaterOrEquals(values, criteria);
            case priceLe:
                return priceLessOrEquals(values, criteria);
            case priceGt:
                return priceGreater(values, criteria);
            case priceLt:
                return priceLess(values, criteria);
            case priceBtwn:
                return priceBetween(values, criteria);
            case orderStateIn:
                return orderStateIn(values, criteria);
            default:
                throw new IllegalArgumentException("Invalid filter");
        }
    }

    private Criterion idEquals(List<String> values, Criteria criteria) {
        List<Integer> ids = FilterUtils.convertStringListToIntegerList(values);

        return Restrictions.in("id", ids);
    }

    private Criterion idNotEquals(List<String> values, Criteria criteria) {
        List<Integer> ids = FilterUtils.convertStringListToIntegerList(values);

        return Restrictions.not(Restrictions.in("id", ids));
    }

    private Criterion idGreaterOrEquals(List<String> values, Criteria criteria) {
        return Restrictions.ge("id", Integer.parseInt(values.get(0)));
    }

    private Criterion idLessOrEquals(List<String> values, Criteria criteria) {
        return Restrictions.le("id", Integer.parseInt(values.get(0)));
    }

    private Criterion idGreater(List<String> values, Criteria criteria) {
        return Restrictions.gt("id", Integer.parseInt(values.get(0)));
    }

    private Criterion idLess(List<String> values, Criteria criteria) {
        return Restrictions.lt("id", Integer.parseInt(values.get(0)));
    }

    private Criterion idBetween(List<String> values, Criteria criteria) {
        return Restrictions.between("id", Integer.parseInt(values.get(0)), Integer.parseInt(values.get(1)));
    }

    private Criterion priceEquals(List<String> values, Criteria criteria) {
        return FilterUtils.createDisjunction(new FilterUtils.CriterionFactory() {
            @Override
            public Criterion createCriterion(String value) {
                return Restrictions.eq("price",
                        EntityUtils.round(BigDecimal.valueOf(Integer.parseInt(value))));
            }
        }, values);
    }

    private Criterion priceNotEquals(List<String> values, Criteria criteria) {
        return FilterUtils.createConjunction(new FilterUtils.CriterionFactory() {
            @Override
            public Criterion createCriterion(String value) {
                return Restrictions.not(Restrictions.eq("price",
                        EntityUtils.round(BigDecimal.valueOf(Integer.parseInt(value)))));
            }
        }, values);
    }

    private Criterion priceGreaterOrEquals(List<String> values, Criteria criteria) {
        return Restrictions.ge("price", EntityUtils.round(BigDecimal.valueOf(Integer.parseInt(values.get(0)))));
    }

    private Criterion priceLessOrEquals(List<String> values, Criteria criteria) {
        return Restrictions.le("price", EntityUtils.round(BigDecimal.valueOf(Integer.parseInt(values.get(0)))));
    }

    private Criterion priceGreater(List<String> values, Criteria criteria) {
        return Restrictions.gt("price", EntityUtils.round(BigDecimal.valueOf(Integer.parseInt(values.get(0)))));
    }

    private Criterion priceLess(List<String> values, Criteria criteria) {
        return Restrictions.lt("price", EntityUtils.round(BigDecimal.valueOf(Integer.parseInt(values.get(0)))));
    }

    private Criterion priceBetween(List<String> values, Criteria criteria) {
        return Restrictions.between("price",
                EntityUtils.round(BigDecimal.valueOf(Integer.parseInt(values.get(0)))),
                EntityUtils.round(BigDecimal.valueOf(Integer.parseInt(values.get(1)))));
    }

    private Criterion nameLike(List<String> values, Criteria criteria) {
        return FilterUtils.createDisjunction(new FilterUtils.CriterionFactory() {
            @Override
            public Criterion createCriterion(String value) {
                return Restrictions.like("name", value, MatchMode.ANYWHERE);
            }
        }, values);
    }

    private Criterion nameNotLike(List<String> values, Criteria criteria) {
        return FilterUtils.createConjunction(new FilterUtils.CriterionFactory() {
            @Override
            public Criterion createCriterion(String value) {
                return Restrictions.not(Restrictions.like("name", value, MatchMode.ANYWHERE));
            }
        }, values);
    }

    private Criterion descriptionLike(List<String> values, Criteria criteria) {
        return FilterUtils.createDisjunction(new FilterUtils.CriterionFactory() {
            @Override
            public Criterion createCriterion(String value) {
                return Restrictions.like("description", value, MatchMode.ANYWHERE);
            }
        }, values);
    }

    private Criterion descriptionNotLike(List<String> values, Criteria criteria) {
        return FilterUtils.createConjunction(new FilterUtils.CriterionFactory() {
            @Override
            public Criterion createCriterion(String value) {
                return Restrictions.not(Restrictions.like("description", value, MatchMode.ANYWHERE));
            }
        }, values);
    }

    private Criterion categoryLike(List<String> values, Criteria criteria) {
        addAlias(criteria, "category", "c");
        return FilterUtils.createDisjunction(new FilterUtils.CriterionFactory() {
            @Override
            public Criterion createCriterion(String value) {
                return Restrictions.like("c.name", value, MatchMode.ANYWHERE);
            }
        }, values);
    }

    private Criterion categoryNotLike(List<String> values, Criteria criteria) {
        addAlias(criteria, "category", "c");
        return FilterUtils.createConjunction(new FilterUtils.CriterionFactory() {
            @Override
            public Criterion createCriterion(String value) {
                return Restrictions.not(Restrictions.like("c.name", value, MatchMode.ANYWHERE));
            }
        }, values);
    }

    private Criterion orderStateIn(List<String> values, Criteria criteria) {
        addAlias(criteria, "orders", "o", JoinType.LEFT_OUTER_JOIN);

        return Restrictions.or(
                Restrictions.isEmpty("orders"),
                Restrictions.in("o.currentOrderStateName", values)
        );
    }
}
