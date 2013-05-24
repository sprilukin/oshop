package oshop.web.api.rest;

import org.hibernate.Criteria;

public class ControllerUtils {

    public static void resetCriteria(Criteria criteria) {
        criteria.setProjection(null);
        criteria.setResultTransformer(Criteria.ROOT_ENTITY);
    }
}
