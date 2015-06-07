package oshop.services.filter;

import org.hibernate.Criteria;

import java.util.List;
import java.util.Map;

public interface Filter {
    public void apply(Map<String, List<String>> filters, Criteria criteria);
}
