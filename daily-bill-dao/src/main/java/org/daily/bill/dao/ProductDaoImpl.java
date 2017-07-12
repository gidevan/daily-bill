package org.daily.bill.dao;

import org.daily.bill.api.dao.ProductDao;
import org.daily.bill.domain.Product;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vano on 31.8.16.
 */
@Repository
public class ProductDaoImpl extends AbstractCrudDao<Product, Long> implements ProductDao {

    @Override
    protected String getNamespace() {
        return "ProductDao";
    }

    @Override
    public List<Product> findProducts() {
        return getSqlSession().selectList(getNamespace() + ".findProducts");
    }

    @Override
    public BigDecimal findLastPrice(Long shopId, Long productId) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("shopId", shopId);
        queryParams.put("productId", productId);
        return getSqlSession().selectOne(getNamespace() + ".findLastPrice", queryParams);
    }
}
