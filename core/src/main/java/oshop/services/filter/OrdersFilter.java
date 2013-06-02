package oshop.services.filter;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import oshop.model.EntityUtils;

import java.math.BigDecimal;
import java.util.List;

@Component
public class OrdersFilter extends BaseFilter {

    static enum OrderFilterNames {
        dateEquals("date"),
        customerLike("customer"),
        productsCountEquals("productsCount"),
        productsCountGreaterOrEquals("productsCountGE"),
        productsCountLessOrEquals("productsCountLE"),
        productsPriceEquals("productsPrice"),
        productsPriceGreaterOrEquals("productsPriceGE"),
        productsPriceLessOrEquals("productsPriceLE"),
        totalPriceEquals("totalPrice"),
        totalPriceGreaterOrEquals("totalPriceGE"),
        totalPriceLessOrEquals("totalPriceLE"),
        currentOrderStateLike("currentOrderStateName");

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
            case dateEquals:
                return dateEquals(values.get(0), criteria);
            case customerLike:
                return customerLike(values, criteria);
            case productsCountEquals:
                return productsCount(values.get(0), criteria);
            case productsCountGreaterOrEquals:
                return productsCountGE(values.get(0), criteria);
            case productsCountLessOrEquals:
                return productsCountLE(values.get(0), criteria);
            case productsPriceEquals:
                return productsPrice(values.get(0), criteria);
            case productsPriceGreaterOrEquals:
                return productsPriceGE(values.get(0), criteria);
            case productsPriceLessOrEquals:
                return productsPriceLE(values.get(0), criteria);
            case totalPriceEquals:
                return totalPrice(values.get(0), criteria);
            case totalPriceGreaterOrEquals:
                return totalPriceGE(values.get(0), criteria);
            case totalPriceLessOrEquals:
                return totalPriceLE(values.get(0), criteria);
            case currentOrderStateLike:
                return currentOrderStateName(values, criteria);
            default:
                throw new IllegalArgumentException("Invalid filter");
        }
    }

    private Criterion dateEquals(String value, Criteria criteria) {
        return Restrictions.eq("date", value);
    }

    private Criterion customerLike(List<String> values, Criteria criteria) {
        addAlias(criteria, "customer", "c");

        Disjunction disjunction = Restrictions.disjunction();
        for (String likeExpression : values) {
            disjunction.add(Restrictions.like("c.name", likeExpression, MatchMode.ANYWHERE));
        }

        return disjunction;
    }

    private Criterion productsCount(String value, Criteria criteria) {
        return Restrictions.eq("productsCount", Integer.parseInt(value));
    }

    private Criterion productsCountGE(String value, Criteria criteria) {
        return Restrictions.ge("productsCount", Integer.parseInt(value));
    }

    private Criterion productsCountLE(String value, Criteria criteria) {
        return Restrictions.le("productsCount", Integer.parseInt(value));
    }

    private Criterion productsPrice(String value, Criteria criteria) {
        return Restrictions.eq("productsPrice", EntityUtils.round(BigDecimal.valueOf(Integer.parseInt(value))));
    }

    private Criterion productsPriceGE(String value, Criteria criteria) {
        return Restrictions.ge("productsPrice", EntityUtils.round(BigDecimal.valueOf(Integer.parseInt(value))));
    }

    private Criterion productsPriceLE(String value, Criteria criteria) {
        return Restrictions.le("productsPrice", EntityUtils.round(BigDecimal.valueOf(Integer.parseInt(value))));
    }

    private Criterion totalPrice(String value, Criteria criteria) {
        return Restrictions.eq("totalPrice", EntityUtils.round(BigDecimal.valueOf(Integer.parseInt(value))));
    }

    private Criterion totalPriceGE(String value, Criteria criteria) {
        return Restrictions.ge("totalPrice", EntityUtils.round(BigDecimal.valueOf(Integer.parseInt(value))));
    }

    private Criterion totalPriceLE(String value, Criteria criteria) {
        return Restrictions.le("totalPrice", EntityUtils.round(BigDecimal.valueOf(Integer.parseInt(value))));
    }

    private Criterion currentOrderStateName(List<String> values, Criteria criteria) {
        Disjunction disjunction = Restrictions.disjunction();
        for (String likeExpression : values) {
            disjunction.add(Restrictions.like("currentOrderStateName", likeExpression, MatchMode.ANYWHERE));
        }

        return disjunction;
    }

}
