package oshop.web.api.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import oshop.model.Product;
import oshop.model.ProductCategory;
import oshop.services.GenericService;
import oshop.services.ProductCategoryService;
import oshop.web.api.rest.adapter.ListReturningRestCallbackAdapter;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/productCategories")
public class ProductCategoryController extends BaseController<ProductCategory, Integer> {

    private static final Log log = LogFactory.getLog(ProductCategoryController.class);

    @Resource
    protected ProductCategoryService productsCategoryService;

    @Override
    protected GenericService<ProductCategory, Integer> getService() {
        return productsCategoryService;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @RequestMapping(
            value = "/{id}/products",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> products(
            @PathVariable final Integer id,
            @RequestParam(value = "limit", required = false) final Integer limit,
            @RequestParam(value = "offset", required = false) final Integer offset) {

        return itemsWithFiltersAndSorters(id,
                Collections.<String, List<String>>emptyMap(),
                Collections.<String, List<String>>emptyMap(), limit, offset);
    }

    @RequestMapping(
            value = "/{id}/products/{filter}/{sort}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> itemsWithFiltersAndSorters(
            @PathVariable final Integer id,
            @MatrixVariable(pathVar="filter", required = false) final Map<String, List<String>> filters,
            @MatrixVariable(pathVar="sort", required = false) final Map<String, List<String>> sorters,
            @RequestParam(value = "limit", required = false) final Integer limit,
            @RequestParam(value = "offset", required = false) final Integer offset) {

        return new ListReturningRestCallbackAdapter<Product>() {

            @Override
            protected List<Product> getResult() throws Exception {
                return productsCategoryService.getProductsByCategory(id, filters, sorters, limit, offset);
            }
        }.invoke();
    }
}
