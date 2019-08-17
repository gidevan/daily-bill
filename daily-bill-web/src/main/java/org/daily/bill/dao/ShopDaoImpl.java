package org.daily.bill.dao;


import org.daily.bill.domain.Shop;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ShopDaoImpl extends AbstractCrudDao<Shop, Long> implements ShopDao {

    @Override
    public List<Shop> findShops(boolean showActive) {
        Map<String, Object> params = new HashMap<>();
        if(showActive) {
            params.put("showActive", showActive);
        }
        return getSqlSession().selectList(getNamespace() + ".findShops", params);
    }

    @Override
    protected String getNamespace() {
        return "ShopDao";
    }

}
