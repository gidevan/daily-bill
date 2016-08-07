package org.daily.bill.service;

import org.daily.bill.api.dao.DailyBillDao;
import org.daily.bill.api.service.DailyBillService;
import org.daily.bill.domain.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by vano on 31.7.16.
 */
@Service("dailyBillService")
public class DailyBillServiceImpl implements DailyBillService {

    @Autowired
    private DailyBillDao dailyBillDao;

    @Override
    public Shop getShopById(Long id) {
        return dailyBillDao.getShopById(id);
    }

    @Override
    public List<Shop> findShops() {
        return dailyBillDao.findShops();
    }
}
