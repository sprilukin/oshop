package oshop.services.sorter;

import org.hibernate.Criteria;

import java.util.List;
import java.util.Map;

public interface Sorter {
    public void apply(Map<String, List<String>> sorters, Criteria criteria);
}
