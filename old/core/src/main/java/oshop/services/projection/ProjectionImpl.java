package oshop.services.projection;

import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Component
public class ProjectionImpl implements Projection {

    @Resource
    private Map<String, String> projectionsMap;

    @Override
    public void apply(Map<String, List<String>> projections, Criteria criteria) {

        ProjectionList projectionList = Projections.projectionList();

        for (Map.Entry<String, List<String>> entry: projections.entrySet()) {
            String field = entry.getKey();
            String projectionAsString = entry.getValue().get(0);
            String projectionMethod = projectionsMap.get(projectionAsString);

            try {
                Method method = Projections.class.getMethod(projectionMethod, String.class);
                org.hibernate.criterion.Projection projection =
                        (org.hibernate.criterion.Projection)method.invoke(null, field);
                projectionList.add(projection);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        if (projectionList.getLength() > 0) {
            criteria.setProjection(projectionList);
        }
    }
}
