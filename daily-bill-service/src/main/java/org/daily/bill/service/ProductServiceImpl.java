package org.daily.bill.service;

import org.daily.bill.api.dao.ProductDao;
import org.daily.bill.api.service.ProductService;
import org.daily.bill.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by vano on 6.11.17.
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public void create(Product entity) {
        productDao.create(entity);
    }

    @Override
    public int update(Product entity) {
        return productDao.update(entity);
    }

    @Override
    public void delete(Long aLong) {
        productDao.delete(aLong);
    }

    @Override
    public Product findById(Long aLong) {
        return productDao.findById(aLong);
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }
}
