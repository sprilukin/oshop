package oshop.web.api.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import oshop.dao.GenericDao;
import oshop.model.Order;
import oshop.model.Product;
import oshop.web.api.rest.adapter.EntityDetachingRestCallbackAdapter;
import oshop.web.api.rest.adapter.VoidRestCallbackAdapter;
import oshop.web.converter.EntityConverter;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/api/orders")
@Transactional(readOnly = true)
public class OrderController extends BaseController<Order, Integer> {

    private static final Log log = LogFactory.getLog(OrderController.class);

    @Resource
    private GenericDao<Order, Integer> orderDao;

    @Resource
    private GenericDao<Product, Integer> productDao;

    @Resource(name = "orderToDTOConverter")
    private EntityConverter<Order, Integer> converter;

    @Override
    protected GenericDao<Order, Integer> getDao() {
        return orderDao;
    }

    @Override
    protected EntityConverter<Order, Integer> getToDTOConverter() {
        return converter;
    }

    // /api/orders/1/products/batch;ids=1,2/delete
    @RequestMapping(
            value = "/{id}/products/{batch}/delete",
            method = RequestMethod.DELETE)
    @Transactional(readOnly = false)
    public ResponseEntity<?> deleteOrderProduct(
            @PathVariable final Integer id,
            @MatrixVariable(value = "ids", pathVar="batch", required = true) final List<Integer> ids) {

        return new VoidRestCallbackAdapter() {
            @Override
            protected void perform() throws Exception {
                getDao().executeQuery(
                        "delete from order_products p where p.order_id = :id and p.product_id in :ids",
                        new GenericDao.SQLQueryManipulator() {
                            @Override
                            public void manipulateWithQuery(SQLQuery query) {
                                query.setParameterList("ids", ids).setParameter("id", id);
                            }
                        });

                getDao().getSession().flush();
            }
        }.invoke();
    }

    // /api/orders/1/products/batch;ids=1,2/add
    @RequestMapping(
            value = "/{id}/products/{batch}/add",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Transactional(readOnly = false)
    public ResponseEntity<?> addOrderProduct(
            @PathVariable final Integer id,
            @MatrixVariable(value = "ids", pathVar="batch", required = true) final List<Integer> ids) {

        return new VoidRestCallbackAdapter() {
            @Override
            protected void perform() throws Exception {
                Order order = getDao().get(id);

                Criteria productsCriteria = productDao.createCriteria();
                productsCriteria.add(Restrictions.in("id", ids));
                List<Product> products = productDao.list(productsCriteria);


                order.getProducts().addAll(products);
                getDao().update(order);
            }
        }.invoke();
    }
}
