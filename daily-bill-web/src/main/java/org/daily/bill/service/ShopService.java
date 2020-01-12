package org.daily.bill.service;


import org.daily.bill.domain.Shop;
import org.daily.bill.domain.ShopView;

import java.util.List;

public interface ShopService extends BaseService<Shop, Long> {
    List<ShopView> findShopsByRating();
    void calculateShopRating();
}
