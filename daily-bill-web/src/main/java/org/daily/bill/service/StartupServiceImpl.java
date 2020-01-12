package org.daily.bill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StartupServiceImpl implements StartupService {

    @Autowired
    private ShopService shopService;

    @Override
    public void startup() {
        shopService.calculateShopRating();
    }
}
