package oshop.services.sorter;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DefaultSorter implements Sorter {

    public static final String ASC_SORT = "asc";
    public static final String DESC_SORT = "desc";

    public void applySorters(Map<String, List<String>> sorters, Criteria criteria) {
        for (Map.Entry<String, List<String>> entry: sorters.entrySet()) {
            String fieldName = entry.getKey();
            String sortType = entry.getValue().get(0);

            addOrder(fieldName, sortType, criteria);
        }
    }

    protected void addOrder(String column, String order, Criteria criteria) {
        if (ASC_SORT.equalsIgnoreCase(order)) {
            criteria.addOrder(Order.asc(column));
        } else if (DESC_SORT.equals(order)) {
            criteria.addOrder(Order.desc(column));
        }
    }
}
