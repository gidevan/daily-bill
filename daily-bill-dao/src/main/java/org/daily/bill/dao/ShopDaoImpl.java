package org.daily.bill.dao;


import org.daily.bill.api.dao.ShopDao;
import org.daily.bill.domain.Shop;
import org.springframework.stereotype.Repository;

@Repository
public class ShopDaoImpl extends AbstractCrudDao<Shop, Long> implements ShopDao {

    @Override
    protected String getNamespace() {
        return "ShopDao";
    }
}
