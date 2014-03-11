package oshop.services.filter.impl;

import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Component;
import oshop.services.filter.CriterionFactory;
import oshop.services.filter.comparison.Comparison;
import oshop.services.filter.converter.Converter;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class CriterionFactoryImpl implements CriterionFactory {

    @Resource(name ="comparisonMap")
    private Map<String, Comparison> comparisonMap;

    @Resource(name ="converterMap")
    private Map<Class, Converter> converterMap;

    public <T> Criterion getCriterion(String field, List<String> values, String comparisonAsString, Class<T> fieldType) {
        Comparison comparison = comparisonMap.get(comparisonAsString);
        Converter<T> converter = converterMap.get(fieldType);

        if (comparison == null) {
            throw new RuntimeException("No comparison exists for string: " + comparisonAsString);
        }

        if (converter == null) {
            throw new RuntimeException("No converter exists for type: " + fieldType);
        }

        return comparison.getComparison(field, values, converter);
    }
}
