package oshop.services.projection;

import org.hibernate.Criteria;

import java.util.List;
import java.util.Map;

public interface Projection {
    public void apply(Map<String, List<String>> projections, Criteria criteria);
}
