package oshop.web.api.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import oshop.dao.GenericDao;
import oshop.model.EntityUtils;
import oshop.model.Order;
import oshop.model.OrderHasOrderStates;
import oshop.model.Product;
import oshop.web.api.rest.adapter.EntityDetachingRestCallbackAdapter;
import oshop.web.api.rest.adapter.ListReturningRestCallbackAdapter;
import oshop.web.api.rest.adapter.ValidationRestCallbackAdapter;
import oshop.web.api.rest.adapter.VoidRestCallbackAdapter;
import oshop.web.converter.EntityConverter;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/orders")
@Transactional(readOnly = true)
public class OrderController extends BaseController<Order, Integer> {

    private static final Log log = LogFactory.getLog(OrderController.class);

    @Resource
    private GenericDao<Order, Integer> orderDao;

    @Resource
    private GenericDao<Product, Integer> productDao;

    @Resource
    private GenericDao<OrderHasOrderStates, Integer> orderHasOrderStatesDao;

    @Resource(name = "orderToDTOConverter")
    private EntityConverter<Order, Integer> converter;

    @Resource(name = "orderFromDTOConverter")
    private EntityConverter<Order, Integer> fromDtoConverter;

    @Resource(name = "orderHasStateToDTOConverter")
    private EntityConverter<OrderHasOrderStates, Integer> orderHasStateConverter;

    @Resource(name = "productToDTOConverter")
    private EntityConverter<Product, Integer> productConverter;

    @Override
    protected GenericDao<Order, Integer> getDao() {
        return orderDao;
    }

    @Override
    protected EntityConverter<Order, Integer> getToDTOConverter() {
        return converter;
    }

    @Override
    protected EntityConverter<Order, Integer> getFromDTOConverter() {
        return fromDtoConverter;
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
                        "delete from order_products where order_id = :id and product_id in :ids",
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
            method = RequestMethod.POST
            )
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

    @RequestMapping(
            value = "/{id}/products",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Transactional(readOnly = false)
    public ResponseEntity<?> listOrderProduct(
            @PathVariable final Integer id) {

        return new ListReturningRestCallbackAdapter<List<Product>>() {

            private Order order = getDao().get(id);

            @Override
            protected Long getSize() throws Exception {
                return (long)order.getProducts().size();
            }

            @Override
            protected List<Product> getList() throws Exception {
                return productConverter.convert(order.getProducts());
            }
        }.invoke();
    }

    @RequestMapping(
            value = "/{id}/orderHasStates",
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Transactional(readOnly = false)
    public ResponseEntity<?> addOrderHasState(
            @PathVariable final Integer id,
            @RequestBody @Valid final OrderHasOrderStates entity, final BindingResult result) {

        return new ValidationRestCallbackAdapter(result,
                new EntityDetachingRestCallbackAdapter<OrderHasOrderStates, Integer>(orderHasStateConverter) {
                    @Override
                    protected OrderHasOrderStates getResult() throws Exception {
                        Order order = getDao().get(id);
                        entity.setOrder(order);
                        Integer id = orderHasOrderStatesDao.add(entity);

                        return orderHasOrderStatesDao.get(id);
                    }
                }).invoke();
    }

    @RequestMapping(
            value = "/{id}/orderHasStates",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Transactional(readOnly = false)
    public ResponseEntity<?> listOrderHasStates(
            @PathVariable final Integer id) {

        return new ListReturningRestCallbackAdapter<List<OrderHasOrderStates>>() {

            private Order order = getDao().get(id);

            @Override
            protected Long getSize() throws Exception {
                return (long)order.getStates().size();
            }

            @Override
            protected List<OrderHasOrderStates> getList() throws Exception {
                return orderHasStateConverter.convert(order.getStates());
            }
        }.invoke();
    }

    static enum OrderNamedFilters {
        dateEquals("date"),
        customerLike("customer"),
        productsCountEquals("productsCount"),
        productsCountGreaterOrEquals("productsCountGE"),
        productsCountLessOrEquals("productsCountLE"),
        productsPriceEquals("productsPrice"),
        productsPriceGreaterOrEquals("productsPriceGE"),
        productsPriceLessOrEquals("productsPriceLE"),
        totalPriceEquals("totalPrice"),
        totalPriceGreaterOrEquals("totalPriceGE"),
        totalPriceLessOrEquals("totalPriceLE"),
        currentOrderStateLike("currentOrderStateName");

        private String name;

        OrderNamedFilters(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }

        static OrderNamedFilters fromName(String name) {
            for (OrderNamedFilters filter: OrderNamedFilters.values()) {
                if (filter.getName().equals(name)) {
                    return filter;
                }
            }

            throw new IllegalArgumentException();
        }
    }

    @Override
    protected Criterion getRestrictionForFilter(String column, List<String> values, Criteria criteria) {

        switch (OrderNamedFilters.fromName(column)) {
            case dateEquals:
                return dateEquals(values.get(0), criteria);
            case customerLike:
                return customerLike(values.get(0), criteria);
            case productsCountEquals:
                return productsCount(values.get(0), criteria);
            case productsCountGreaterOrEquals:
                return productsCountGE(values.get(0), criteria);
            case productsCountLessOrEquals:
                return productsCountLE(values.get(0), criteria);
            case productsPriceEquals:
                return productsPrice(values.get(0), criteria);
            case productsPriceGreaterOrEquals:
                return productsPriceGE(values.get(0), criteria);
            case productsPriceLessOrEquals:
                return productsPriceLE(values.get(0), criteria);
            case totalPriceEquals:
                return totalPrice(values.get(0), criteria);
            case totalPriceGreaterOrEquals:
                return totalPriceGE(values.get(0), criteria);
            case totalPriceLessOrEquals:
                return totalPriceLE(values.get(0), criteria);
            case currentOrderStateLike:
                return currentOrderStateName(values.get(0), criteria);
            default:
                throw new IllegalArgumentException("Invalid filter");
        }
    }

    private Criterion dateEquals(String value, Criteria criteria) {
        return Restrictions.eq("date", value);
    }

    private Criterion customerLike(String value, Criteria criteria) {
        criteria.createAlias("customer", "c");
        return Restrictions.like("c.name", value, MatchMode.ANYWHERE);
    }

    private Criterion productsCount(String value, Criteria criteria) {
        return Restrictions.eq("productsCount", Integer.parseInt(value));
    }

    private Criterion productsCountGE(String value, Criteria criteria) {
        return Restrictions.ge("productsCount", Integer.parseInt(value));
    }

    private Criterion productsCountLE(String value, Criteria criteria) {
        return Restrictions.le("productsCount", Integer.parseInt(value));
    }

    private Criterion productsPrice(String value, Criteria criteria) {
        return Restrictions.eq("productsPrice", EntityUtils.round(BigDecimal.valueOf(Integer.parseInt(value))));
    }

    private Criterion productsPriceGE(String value, Criteria criteria) {
        return Restrictions.ge("productsPrice", EntityUtils.round(BigDecimal.valueOf(Integer.parseInt(value))));
    }

    private Criterion productsPriceLE(String value, Criteria criteria) {
        return Restrictions.le("productsPrice", EntityUtils.round(BigDecimal.valueOf(Integer.parseInt(value))));
    }

    private Criterion totalPrice(String value, Criteria criteria) {
        return null;
    }

    private Criterion totalPriceGE(String value, Criteria criteria) {
        return null;
    }

    private Criterion totalPriceLE(String value, Criteria criteria) {
        return null;
    }

    private Criterion currentOrderStateName(String value, Criteria criteria) {
        return Restrictions.like("currentOrderStateName", value, MatchMode.ANYWHERE);
    }
}
