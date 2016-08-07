package org.daily.bill.web.controller;

import org.daily.bill.api.dao.DailyBillDao;
import org.daily.bill.api.service.DailyBillService;
import org.daily.bill.domain.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by vano on 31.7.16.
 */
@RestController
@RequestMapping("/daily-bill")
public class DailyBillRest {

    @Autowired
    private DailyBillService dailyBillService;


    @RequestMapping("/info")
    public String info() {
        return "Daily bill app";
    }

    @RequestMapping("/shop/{id}")
    public Shop getShop(@PathVariable Long id) {
        return dailyBillService.getShopById(id);
        //return null;
    }

    @RequestMapping("/shops")
    public List<Shop> findShops() {
        return dailyBillService.findShops();
    }
}
