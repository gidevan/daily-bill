package org.daily.bill.dao;

import org.daily.bill.api.dao.DailyBillDao;
import org.daily.bill.domain.Shop;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by vano on 4.8.16.
 */
@Repository
public class DailyBillDaoImpl extends AbstractDao implements DailyBillDao {

    @Override
    public Shop getShopById(Long id) {
        return getSqlSession().selectOne("getShopById", id);
    }

    @Override
    public List<Shop> findShops() {
        return getSqlSession().selectList("findShops");
    }
}
