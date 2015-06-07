package oshop.services.filter;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

public abstract class ComparisonBasedFilter extends BaseFilter {

    protected abstract Map<String, Class> getFieldsMapping();
    protected abstract Map<String, String> getAliasesMapping();

    @Resource
    private CriterionFactory criterionFactory;

    protected String getField(String fieldWithComparison) {
        for (String name: getFieldsMapping().keySet()) {
            if (fieldWithComparison.startsWith(name)) {
                if (fieldWithComparison.substring(name.length()).matches("[A-Z]{2,10}")) {
                    return name;
                }
            }
        }

        return null;
    }

    protected String applyAliasToField(String field, Criteria criteria) {
        String path = getAliasesMapping().get(field);
        if (path != null) {
            String alias = field.substring(0, 1);
            FilterUtils.addAlias(criteria, field, alias);
            field = String.format("%s.%s", alias, path);
        }

        return field;
    }

    @Override
    protected Criterion getRestrictionForFilter(String column, List<String> values, Criteria criteria) {
        String field = getField(column);

        Criterion criterion = null;

        if (field != null) {
            String comparisonAsString = column.substring(field.length(), column.length());
            Class fieldType = getFieldsMapping().get(field);
            field = applyAliasToField(field, criteria);

            criterion = criterionFactory.getCriterion(field, values, comparisonAsString, fieldType);
        }

        if (criterion == null) {
            criterion = getCustomRestrictionForFilter(column, values, criteria);
        }

        return criterion;
    }

    protected Criterion getCustomRestrictionForFilter(String column, List<String> values, Criteria criteria) {
        return null;
    }
}
