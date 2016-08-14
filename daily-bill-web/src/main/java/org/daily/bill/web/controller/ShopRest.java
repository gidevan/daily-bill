package org.daily.bill.web.controller;

import org.daily.bill.api.service.ShopService;
import org.daily.bill.domain.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by vano on 14.8.16.
 */
@RestController
@RequestMapping("/api/shop")
public class ShopRest {

    @Autowired
    private ShopService shopService;

    @RequestMapping(method = RequestMethod.PUT)
    public void create(@RequestBody Shop shop) {
        shopService.create(shop);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Shop findById(@RequestParam Long id) {
        return shopService.findById(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public List<Shop> findAll() {
        return shopService.findAll();
    }
}
