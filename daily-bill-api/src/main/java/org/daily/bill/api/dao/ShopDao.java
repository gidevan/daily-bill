package org.daily.bill.api.dao;

import org.daily.bill.domain.Shop;

import java.util.List;

/**
 * Created by vano on 14.8.16.
 */
public interface ShopDao extends CrudDao<Shop, Long> {

    List<Shop> findShops(boolean showActive);
}
