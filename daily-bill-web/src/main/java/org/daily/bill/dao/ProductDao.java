package org.daily.bill.dao;

import org.daily.bill.domain.Product;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by vano on 31.8.16.
 */
public interface ProductDao extends CrudDao<Product, Long> {

    List<Product> findProducts(boolean showActive);

    BigDecimal findLastPrice(Long shopId, Long productId);
}
