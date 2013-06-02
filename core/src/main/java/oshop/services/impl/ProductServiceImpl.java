package oshop.services.impl;

import org.springframework.stereotype.Service;
import oshop.dao.GenericDao;
import oshop.model.Product;
import oshop.services.converter.EntityConverter;
import oshop.services.filter.Filter;

import javax.annotation.Resource;

@Service("productService")
public class ProductServiceImpl extends GenericServiceImpl<Product, Integer> {

    @Resource
    protected GenericDao<Product, Integer> productDao;

    @Resource
    protected Filter productsFilter;

    @Resource(name = "productToDTOConverter")
    private EntityConverter<Product, Integer> converter;

    @Override
    protected GenericDao<Product, Integer> getDao() {
        return productDao;
    }

    @Override
    protected EntityConverter<Product, Integer> getToDTOConverter() {
        return converter;
    }

    @Override
    protected Filter getFilter() {
        return productsFilter;
    }
}
