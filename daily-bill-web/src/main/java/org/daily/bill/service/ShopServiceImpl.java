package org.daily.bill.service;

import org.daily.bill.dao.ShopDao;
import org.daily.bill.dao.ShopRatingDao;
import org.daily.bill.domain.Shop;
import org.daily.bill.domain.ShopView;
import org.daily.bill.utils.annotation.Profiling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by vano on 14.8.16.
 */
@Service
@Profiling
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private ShopRatingDao shopRatingDao;

    @Override
    public void create(Shop entity) {
        shopDao.create(entity);
    }

    @Override
    public int update(Shop entity) {
        return shopDao.update(entity);
    }

    @Override
    public void delete(Long id) {
        shopDao.delete(id);
    }

    @Override
    public Shop findById(Long id) {
        return shopDao.findById(id);
    }

    @Override
    public List<Shop> findAll() {
        return shopDao.findAll();
    }

    @Override
    public List<ShopView> findShopsByRating() {
        return shopRatingDao.findShopsByRating();
    }

    @Override
    public void calculateShopRating() {
        shopRatingDao.deleteAll();
        shopRatingDao.calculateShopRating();

    }
}
