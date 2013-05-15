package oshop.web.converter.order;

import org.springframework.stereotype.Component;
import oshop.dao.GenericDao;
import oshop.model.Order;
import oshop.web.converter.EntityConverter;

import javax.annotation.Resource;
import java.util.List;

@Component
public class OrderFromDTOConverter implements EntityConverter<Order, Integer> {

    @Resource
    private GenericDao<Order, Integer> orderDao;

    @Override
    public Order convert(Order entity) throws Exception {
        if (entity.getId() == null) {
            return entity;
        }

        Order persistentEntity = orderDao.get(entity.getId());
        persistentEntity.setDescription(entity.getDescription());
        persistentEntity.setDate(entity.getDate());
        persistentEntity.setCustomer(entity.getCustomer());
        persistentEntity.setDiscount(entity.getDiscount());
        persistentEntity.setPrepayment(entity.getPrepayment());
        persistentEntity.setShippingAddress(entity.getShippingAddress());
        persistentEntity.setVersion(entity.getVersion());

        return persistentEntity;
    }

    @Override
    public List<Order> convert(List<Order> entities) throws Exception {
        throw new IllegalArgumentException("Method not supported");
    }
}
