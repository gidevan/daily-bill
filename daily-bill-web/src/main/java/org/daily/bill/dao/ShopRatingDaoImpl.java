package org.daily.bill.dao;

import org.daily.bill.domain.ShopRating;
import org.springframework.stereotype.Repository;

/**
 * Created by vano on 18.8.19.
 */
@Repository
public class ShopRatingDaoImpl extends AbstractCrudDao<ShopRating, Long> implements ShopRatingDao {

    @Override
    protected String getNamespace() {
        return "ShopRatingDao";
    }
}
