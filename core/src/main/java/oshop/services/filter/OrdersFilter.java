package oshop.services.filter;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import oshop.model.EntityUtils;

import java.math.BigDecimal;
import java.util.List;

@Component
public class OrdersFilter extends BaseFilter {

    static enum OrderFilterNames {
        dateEq("dateEQ"),
        dateNeq("dateNEQ"),
        dateGe("dateGE"),
        dateLe("dateLE"),
        dateGt("dateGT"),
        dateLt("dateLT"),
        dateBtwn("dateBTWN"),
        customerLike("customerLIKE"),
        customerNlike("customerNLIKE"),
        productsCountEq("productsCountEQ"),
        productsCountNeq("productsCountNEQ"),
        productsCountGe("productsCountGE"),
        productsCountLe("productsCountLE"),
        productsCountGt("productsCountGT"),
        productsCountLt("productsCountLT"),
        productsCountBtwn("productsCountBTWN"),
        productsPriceEq("productsPriceEQ"),
        productsPriceNeq("productsPriceNEQ"),
        productsPriceGe("productsPriceGE"),
        productsPriceLe("productsPriceLE"),
        productsPriceGt("productsPriceGT"),
        productsPriceLt("productsPriceLT"),
        productsPriceBtwn("productsPriceBTWN"),
        totalPriceEq("totalPriceEQ"),
        totalPriceNeq("totalPriceNEQ"),
        totalPriceGe("totalPriceGE"),
        totalPriceLe("totalPriceLE"),
        totalPriceGt("totalPriceGT"),
        totalPriceLt("totalPriceLT"),
        totalPriceBtwn("totalPriceBTWN"),
        currentOrderStateLike("currentOrderStateNameLIKE"),
        currentOrderStateNlike("currentOrderStateNameNLIKE"),
        orderStateNotIn("orderStateNotIn");

        private String name;

        OrderFilterNames(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }

        static OrderFilterNames fromName(String name) {
            for (OrderFilterNames filter: OrderFilterNames.values()) {
                if (filter.getName().equals(name)) {
                    return filter;
                }
            }

            throw new IllegalArgumentException();
        }
    }

    @Override
    protected Criterion getRestrictionForFilter(String column, List<String> values, Criteria criteria) {

        switch (OrderFilterNames.fromName(column)) {
            case dateEq:
                return dateEquals(values, criteria);
            case dateNeq:
                return dateNotEquals(values, criteria);
            case dateGe:
                return dateGreaterOrEquals(values, criteria);
            case dateLe:
                return dateLessOrEquals(values, criteria);
            case dateGt:
                return dateGreater(values, criteria);
            case dateLt:
                return dateLess(values, criteria);
            case dateBtwn:
                return dateBetween(values, criteria);
            case customerLike:
                return customerLike(values, criteria);
            case customerNlike:
                return customerNlike(values, criteria);
            case productsCountEq:
                return productsCountEquals(values, criteria);
            case productsCountNeq:
                return productsCountNotEquals(values, criteria);
            case productsCountGe:
                return productsCountGreaterOrEquals(values, criteria);
            case productsCountLe:
                return productsCountLessOrEquals(values, criteria);
            case productsCountGt:
                return productsCountGreater(values, criteria);
            case productsCountLt:
                return productsCountLess(values, criteria);
            case productsCountBtwn:
                return productsCountBetween(values, criteria);
            case productsPriceEq:
                return productsPriceEquals(values, criteria);
            case productsPriceNeq:
                return productsPriceNotEquals(values, criteria);
            case productsPriceGe:
                return productsPriceGreaterOrEquals(values, criteria);
            case productsPriceLe:
                return productsPriceLessOrEquals(values, criteria);
            case productsPriceGt:
                return productsPriceGreater(values, criteria);
            case productsPriceLt:
                return productsPriceLess(values, criteria);
            case productsPriceBtwn:
                return productsPriceBetween(values, criteria);
            case totalPriceEq:
                return totalPriceEquals(values, criteria);
            case totalPriceNeq:
                return totalPriceNotEquals(values, criteria);
            case totalPriceGe:
                return totalPriceGreaterOrEquals(values, criteria);
            case totalPriceLe:
                return totalPriceLessOrEquals(values, criteria);
            case totalPriceGt:
                return totalPriceGreater(values, criteria);
            case totalPriceLt:
                return totalPriceLess(values, criteria);
            case totalPriceBtwn:
                return totalPriceBetween(values, criteria);
            case currentOrderStateLike:
                return currentOrderStateNameLike(values, criteria);
            case currentOrderStateNlike:
                return currentOrderStateNameNotLike(values, criteria);
            case orderStateNotIn:
                return orderStateNotIn(values, criteria);
            default:
                throw new IllegalArgumentException("Invalid filter");
        }
    }

    private Criterion dateEquals(List<String> values, Criteria criteria) {
        return FilterUtils.createDisjunction(new FilterUtils.CriterionFactory() {
            @Override
            public Criterion createCriterion(String value) {
                return Restrictions.eq("date", FilterUtils.parseDate(value));
            }
        }, values);
    }

    private Criterion dateNotEquals(List<String> values, Criteria criteria) {
        return FilterUtils.createConjunction(new FilterUtils.CriterionFactory() {
            @Override
            public Criterion createCriterion(String value) {
                return Restrictions.not(Restrictions.eq("date", FilterUtils.parseDate(value)));
            }
        }, values);
    }

    private Criterion dateGreaterOrEquals(List<String> values, Criteria criteria) {
        return Restrictions.ge("date", FilterUtils.parseDate(values.get(0)));
    }

    private Criterion dateLessOrEquals(List<String> values, Criteria criteria) {
        return Restrictions.le("date", FilterUtils.parseDate(values.get(0)));
    }

    private Criterion dateGreater(List<String> values, Criteria criteria) {
        return Restrictions.gt("date", FilterUtils.parseDate(values.get(0)));
    }

    private Criterion dateLess(List<String> values, Criteria criteria) {
        return Restrictions.lt("date", FilterUtils.parseDate(values.get(0)));
    }

    private Criterion dateBetween(List<String> values, Criteria criteria) {
        return Restrictions.between(
                "date",
                FilterUtils.parseDate(values.get(0)), FilterUtils.parseDate(values.get(1)));
    }

    private Criterion customerLike(List<String> values, Criteria criteria) {
        addAlias(criteria, "customer", "c");
        return FilterUtils.createDisjunction(new FilterUtils.CriterionFactory() {
            @Override
            public Criterion createCriterion(String value) {
                return Restrictions.like("c.name", value, MatchMode.ANYWHERE);
            }
        }, values);
    }

    private Criterion customerNlike(List<String> values, Criteria criteria) {
        addAlias(criteria, "customer", "c");
        return FilterUtils.createConjunction(new FilterUtils.CriterionFactory() {
            @Override
            public Criterion createCriterion(String value) {
                return Restrictions.not(Restrictions.like("c.name", value, MatchMode.ANYWHERE));
            }
        }, values);
    }

    private Criterion productsCountEquals(List<String> values, Criteria criteria) {
        return FilterUtils.createDisjunction(new FilterUtils.CriterionFactory() {
            @Override
            public Criterion createCriterion(String value) {
                return Restrictions.eq("productsCount", Integer.parseInt(value));
            }
        }, values);
    }

    private Criterion productsCountNotEquals(List<String> values, Criteria criteria) {
        return FilterUtils.createConjunction(new FilterUtils.CriterionFactory() {
            @Override
            public Criterion createCriterion(String value) {
            return Restrictions.not(Restrictions.eq("productsCount", Integer.parseInt(value)));
            }
        }, values);
    }

    private Criterion productsCountGreaterOrEquals(List<String> values, Criteria criteria) {
        return Restrictions.ge("productsCount", Integer.parseInt(values.get(0)));
    }

    private Criterion productsCountLessOrEquals(List<String> values, Criteria criteria) {
        return Restrictions.le("productsCount", Integer.parseInt(values.get(0)));
    }

    private Criterion productsCountGreater(List<String> values, Criteria criteria) {
        return Restrictions.gt("productsCount", Integer.parseInt(values.get(0)));
    }

    private Criterion productsCountLess(List<String> values, Criteria criteria) {
        return Restrictions.lt("productsCount", Integer.parseInt(values.get(0)));
    }

    private Criterion productsCountBetween(List<String> values, Criteria criteria) {
        return Restrictions.between("productsCount",
                Integer.parseInt(values.get(0)), Integer.parseInt(values.get(1)));
    }

    private Criterion productsPriceEquals(List<String> values, Criteria criteria) {
        return FilterUtils.createDisjunction(new FilterUtils.CriterionFactory() {
            @Override
            public Criterion createCriterion(String value) {
                return Restrictions.eq("productsPrice",
                        EntityUtils.round(BigDecimal.valueOf(Integer.parseInt(value))));
            }
        }, values);
    }

    private Criterion productsPriceNotEquals(List<String> values, Criteria criteria) {
        return FilterUtils.createConjunction(new FilterUtils.CriterionFactory() {
            @Override
            public Criterion createCriterion(String value) {
                return Restrictions.not(Restrictions.eq("productsPrice",
                        EntityUtils.round(BigDecimal.valueOf(Integer.parseInt(value)))));
            }
        }, values);
    }

    private Criterion productsPriceGreaterOrEquals(List<String> values, Criteria criteria) {
        return Restrictions.ge("productsPrice",
                EntityUtils.round(BigDecimal.valueOf(Integer.parseInt(values.get(0)))));
    }

    private Criterion productsPriceLessOrEquals(List<String> values, Criteria criteria) {
        return Restrictions.le("productsPrice",
                EntityUtils.round(BigDecimal.valueOf(Integer.parseInt(values.get(0)))));
    }

    private Criterion productsPriceGreater(List<String> values, Criteria criteria) {
        return Restrictions.gt("productsPrice",
                EntityUtils.round(BigDecimal.valueOf(Integer.parseInt(values.get(0)))));
    }

    private Criterion productsPriceLess(List<String> values, Criteria criteria) {
        return Restrictions.lt("productsPrice",
                EntityUtils.round(BigDecimal.valueOf(Integer.parseInt(values.get(0)))));
    }

    private Criterion productsPriceBetween(List<String> values, Criteria criteria) {
        return Restrictions.between("productsPrice",
                EntityUtils.round(BigDecimal.valueOf(Integer.parseInt(values.get(0)))),
                EntityUtils.round(BigDecimal.valueOf(Integer.parseInt(values.get(1)))));
    }

    private Criterion totalPriceEquals(List<String> values, Criteria criteria) {
        return FilterUtils.createDisjunction(new FilterUtils.CriterionFactory() {
            @Override
            public Criterion createCriterion(String value) {
                return Restrictions.eq("totalPrice",
                        EntityUtils.round(BigDecimal.valueOf(Integer.parseInt(value))));
            }
        }, values);
    }

    private Criterion totalPriceNotEquals(List<String> values, Criteria criteria) {
        return FilterUtils.createConjunction(new FilterUtils.CriterionFactory() {
            @Override
            public Criterion createCriterion(String value) {
                return Restrictions.not(Restrictions.eq("totalPrice",
                        EntityUtils.round(BigDecimal.valueOf(Integer.parseInt(value)))));
            }
        }, values);
    }

    private Criterion totalPriceGreaterOrEquals(List<String> values, Criteria criteria) {
        return Restrictions.ge("totalPrice", EntityUtils.round(BigDecimal.valueOf(Integer.parseInt(values.get(0)))));
    }

    private Criterion totalPriceLessOrEquals(List<String> values, Criteria criteria) {
        return Restrictions.le("totalPrice", EntityUtils.round(BigDecimal.valueOf(Integer.parseInt(values.get(0)))));
    }

    private Criterion totalPriceGreater(List<String> values, Criteria criteria) {
        return Restrictions.gt("totalPrice", EntityUtils.round(BigDecimal.valueOf(Integer.parseInt(values.get(0)))));
    }

    private Criterion totalPriceLess(List<String> values, Criteria criteria) {
        return Restrictions.lt("totalPrice", EntityUtils.round(BigDecimal.valueOf(Integer.parseInt(values.get(0)))));
    }

    private Criterion totalPriceBetween(List<String> values, Criteria criteria) {
        return Restrictions.between("totalPrice",
                EntityUtils.round(BigDecimal.valueOf(Integer.parseInt(values.get(0)))),
                EntityUtils.round(BigDecimal.valueOf(Integer.parseInt(values.get(1)))));
    }

    private Criterion currentOrderStateNameLike(List<String> values, Criteria criteria) {
        return FilterUtils.createDisjunction(new FilterUtils.CriterionFactory() {
            @Override
            public Criterion createCriterion(String value) {
                return Restrictions.like("currentOrderStateName", value, MatchMode.ANYWHERE);
            }
        }, values);
    }

    private Criterion currentOrderStateNameNotLike(List<String> values, Criteria criteria) {
        return FilterUtils.createConjunction(new FilterUtils.CriterionFactory() {
            @Override
            public Criterion createCriterion(String value) {
                return Restrictions.not(Restrictions.like("currentOrderStateName", value, MatchMode.ANYWHERE));
            }
        }, values);
    }

    private Criterion orderStateNotIn(List<String> values, Criteria criteria) {
        return Restrictions.or(
                Restrictions.isNull("currentOrderStateName"),
                Restrictions.not(Restrictions.in("currentOrderStateName", values))
        );
    }
}
