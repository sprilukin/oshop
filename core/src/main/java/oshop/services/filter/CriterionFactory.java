package oshop.services.filter;

import org.hibernate.criterion.Criterion;

import java.util.List;

public interface CriterionFactory {
    public <T> Criterion getCriterion(String field, List<String> values, String comparisonAsString, Class<T> fieldType);
}
