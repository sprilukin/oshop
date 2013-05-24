package oshop.web.api.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import oshop.dao.GenericDao;
import oshop.model.Product;
import oshop.model.ProductCategory;
import oshop.web.api.rest.adapter.EntityListDetachingRestCallbackAdapter;
import oshop.web.converter.EntityConverter;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/productCategories")
@Transactional(readOnly = true)
public class ProductCategoryController extends BaseController<ProductCategory, Integer> {

    private static final Log log = LogFactory.getLog(ProductCategoryController.class);

    @Resource
    private GenericDao<ProductCategory, Integer> productCategoryDao;

    @Resource
    private GenericDao<Product, Integer> productDao;

    @Resource(name = "productCategoryToDTOConverter")
    private EntityConverter<ProductCategory, Integer> converter;

    @Resource(name = "productToDTOConverter")
    private EntityConverter<Product, Integer> productConverter;

    @Override
    protected GenericDao<ProductCategory, Integer> getDao() {
        return productCategoryDao;
    }

    @Override
    protected EntityConverter<ProductCategory, Integer> getToDTOConverter() {
        return converter;
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

        return new EntityListDetachingRestCallbackAdapter<Product, Integer>(productConverter, getSearchDao()) {

            @Override
            protected Criteria getCriteria() {
                Criteria criteria = productDao.createCriteria();
                criteria.createAlias("category", "c").add(Restrictions.eq("c.id", id));
                applyFilters(filters, criteria);
                applySorters(sorters, criteria);

                return criteria;
            }

            @Override
            protected List<Product> getList(Criteria criteria) {
                return productDao.list(criteria, offset, limit);
            }
        }.invoke();
    }
}
