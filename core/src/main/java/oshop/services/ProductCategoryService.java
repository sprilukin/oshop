package oshop.services;

import org.springframework.transaction.annotation.Transactional;
import oshop.dto.PaginatedList;
import oshop.model.Product;
import oshop.model.ProductCategory;

import java.util.List;
import java.util.Map;

@Transactional(readOnly = true)
public interface ProductCategoryService extends GenericService<ProductCategory, Integer> {

    public PaginatedList<Product> getProductsByCategory(
            Integer id, Map<String, List<String>> filters, Map<String, List<String>> sorters,
            Integer limit, Integer offset)
                throws Exception;
}
