package oshop.web.api.rest;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.Map;

public class ControllerUtils {

    public static void applyFilters(Map<String, List<String>> filters, Criteria criteria) {
        Disjunction disjunction = Restrictions.disjunction();
        for (Map.Entry<String, List<String>> entry: filters.entrySet()) {
            for (String likeExpression: entry.getValue()) {
                disjunction.add(Restrictions.like(entry.getKey(), likeExpression, MatchMode.ANYWHERE));
            }
        }

        criteria.add(disjunction);
    }

    public static void applySorters(Map<String, List<String>> sorters, Criteria criteria) {
        for (Map.Entry<String, List<String>> entry: sorters.entrySet()) {
            String fieldName = entry.getKey();
            String sortType = entry.getValue().get(0);

            if ("asc".equalsIgnoreCase(sortType)) {
                criteria.addOrder(Order.asc(fieldName));
            } else if ("desc".equals(sortType)) {
                criteria.addOrder(Order.desc(fieldName));
            }
        }
    }

}