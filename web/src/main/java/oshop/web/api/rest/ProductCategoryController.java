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
import oshop.web.converter.DefaultNoDetachConverter;
import oshop.web.converter.EntityDetachConverter;
import oshop.web.converter.ProductConverter;

import javax.annotation.Resource;
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

    @Override
    protected GenericDao<ProductCategory, Integer> getDao() {
        return productCategoryDao;
    }

    @Override
    protected EntityDetachConverter<ProductCategory, Integer> getDefaultConverter() {
        return new DefaultNoDetachConverter<ProductCategory, Integer>();
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

        return new EntityListDetachingRestCallbackAdapter<Product, Integer>(new ProductConverter(), getSearchDao()) {

            @Override
            protected Criteria getCriteria() {
                Criteria criteria = productDao.createCriteria();
                criteria.createAlias("category", "c").add(Restrictions.eq("c.id", id));

                return criteria;
            }

            @Override
            protected List<Product> getList(Criteria criteria) {
                return productDao.list(criteria, offset, limit);
            }
        }.invoke();
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

        return new EntityListDetachingRestCallbackAdapter<Product, Integer>(new ProductConverter(), getSearchDao()) {

            @Override
            protected Criteria getCriteria() {
                Criteria criteria = productDao.createCriteria();
                criteria.createAlias("category", "c").add(Restrictions.eq("c.id", id));
                ControllerUtils.applyFilters(filters, criteria);
                ControllerUtils.applySorters(sorters, criteria);

                return criteria;
            }

            @Override
            protected List<Product> getList(Criteria criteria) {
                return productDao.list(criteria, offset, limit);
            }
        }.invoke();
    }
}
