package oshop.web.api.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import oshop.model.Order;
import oshop.model.OrderHasOrderStates;
import oshop.model.Product;
import oshop.services.GenericService;
import oshop.services.OrderService;
import oshop.web.api.rest.adapter.ListReturningRestCallbackAdapter;
import oshop.web.api.rest.adapter.ReturningRestCallbackAdapter;
import oshop.web.api.rest.adapter.ValidationRestCallbackAdapter;
import oshop.web.api.rest.adapter.VoidRestCallbackAdapter;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/api/orders")
public class OrderController extends BaseController<Order, Integer> {

    private static final Log log = LogFactory.getLog(OrderController.class);

    @Resource
    protected OrderService orderService;

    @Override
    protected GenericService<Order, Integer> getService() {
        return orderService;
    }

    // /api/orders/1/products/batch;ids=1,2/delete
    @RequestMapping(
            value = "/{id}/products/{batch}/delete",
            method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOrderProduct(
            @PathVariable final Integer id,
            @MatrixVariable(value = "ids", pathVar="batch", required = true) final List<Integer> ids) {

        return new VoidRestCallbackAdapter() {
            @Override
            protected void perform() throws Exception {
                orderService.deleteProductsFromOrder(id, ids);
            }
        }.invoke();
    }

    // /api/orders/1/products/batch;ids=1,2/add
    @RequestMapping(
            value = "/{id}/products/{batch}/add",
            method = RequestMethod.POST
            )
    @ResponseBody
    public ResponseEntity<?> addOrderProduct(
            @PathVariable final Integer id,
            @MatrixVariable(value = "ids", pathVar="batch", required = true) final List<Integer> ids) {

        return new VoidRestCallbackAdapter() {
            @Override
            protected void perform() throws Exception {
                orderService.addProductsToOrder(id, ids);
            }
        }.invoke();
    }

    @RequestMapping(
            value = "/{id}/products",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> listOrderProduct(
            @PathVariable final Integer id) {

        return new ListReturningRestCallbackAdapter<Product>() {
            @Override
            protected List<Product> getResult() throws Exception {
                return orderService.getProductsByOrder(id);
            }
        }.invoke();
    }

    @RequestMapping(
            value = "/{id}/orderHasStates",
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> addOrderHasState(
            @PathVariable final Integer id,
            @RequestBody @Valid final OrderHasOrderStates entity, final BindingResult result) {

        return new ValidationRestCallbackAdapter(result,
                new ReturningRestCallbackAdapter<OrderHasOrderStates>() {
                    @Override
                    protected OrderHasOrderStates getResult() throws Exception {
                        return orderService.addOrderHasStateToOrder(id, entity);
                    }
                }).invoke();
    }

    @RequestMapping(
            value = "/{id}/orderHasStates",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> listOrderHasStates(
            @PathVariable final Integer id) {

        return new ListReturningRestCallbackAdapter<OrderHasOrderStates>() {
            @Override
            protected List<OrderHasOrderStates> getResult() throws Exception {
                return orderService.getOrderHasStatesByOrder(id);
            }
        }.invoke();
    }
}
