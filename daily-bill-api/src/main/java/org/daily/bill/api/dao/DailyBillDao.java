package org.daily.bill.api.dao;

import org.daily.bill.domain.Shop;

import java.util.List;

/**
 * Created by vano on 31.7.16.
 */
public interface DailyBillDao {

    Shop getShopById(Long id);

    List<Shop> findShops();
}
