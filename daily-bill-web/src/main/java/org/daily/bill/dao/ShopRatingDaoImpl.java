package org.daily.bill.dao;

import org.daily.bill.domain.ShopRating;
import org.daily.bill.domain.ShopView;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by vano on 18.8.19.
 */
@Repository
public class ShopRatingDaoImpl extends AbstractCrudDao<ShopRating, Long> implements ShopRatingDao {

    @Override
    public List<ShopView> findShopsByRating() {
        return getSqlSession().selectList(getNamespace() + ".findShopsByRating");
    }

    @Override
    protected String getNamespace() {
        return "ShopRatingDao";
    }
}
