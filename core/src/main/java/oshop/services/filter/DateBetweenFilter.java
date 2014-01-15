package oshop.services.filter;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DateBetweenFilter extends BaseFilter {

    static enum ExpenseFilterNames {
        dateBtwn("dateBTWN");

        private String name;

        ExpenseFilterNames(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }

        static ExpenseFilterNames fromName(String name) {
            for (ExpenseFilterNames filter: ExpenseFilterNames.values()) {
                if (filter.getName().equals(name)) {
                    return filter;
                }
            }

            throw new IllegalArgumentException();
        }
    }

    @Override
    protected Criterion getRestrictionForFilter(String column, List<String> values, Criteria criteria) {

        switch (ExpenseFilterNames.fromName(column)) {
            case dateBtwn:
                return dateBetween(values, criteria);
            default:
                throw new IllegalArgumentException("Invalid filter");
        }
    }

    private Criterion dateBetween(List<String> values, Criteria criteria) {
        return Restrictions.between(
                "date",
                FilterUtils.parseDate(values.get(0)), FilterUtils.parseDate(values.get(1)));
    }
}
