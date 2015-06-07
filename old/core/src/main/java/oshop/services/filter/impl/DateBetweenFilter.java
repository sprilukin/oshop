package oshop.services.filter.impl;

import org.springframework.stereotype.Component;
import oshop.services.filter.ComparisonBasedFilter;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class DateBetweenFilter extends ComparisonBasedFilter {

    @Resource
    private Map<String, Class> dateBeetweenFieldsMapping;

    @Resource
    private Map<String, String> dateBeetweenAliasesMapping;

    @Override
    protected Map<String, Class> getFieldsMapping() {
        return dateBeetweenFieldsMapping;
    }

    @Override
    protected Map<String, String> getAliasesMapping() {
        return dateBeetweenAliasesMapping;
    }
}
