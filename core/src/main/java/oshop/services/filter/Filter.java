package oshop.services.filter;

import org.hibernate.Criteria;

import java.util.List;
import java.util.Map;

public interface Filter {
    public void applyFilters(Map<String, List<String>> filters, Criteria criteria);
}
