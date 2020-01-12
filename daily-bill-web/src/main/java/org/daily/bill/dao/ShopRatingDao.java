package org.daily.bill.dao;

import org.daily.bill.domain.ShopRating;
import org.daily.bill.domain.ShopView;

import java.util.List;

/**
 * Created by vano on 18.8.19.
 */
public interface ShopRatingDao extends CrudDao<ShopRating, Long> {

    List<ShopView> findShopsByRating();

    void deleteAll();

    void calculateShopRating();
}
