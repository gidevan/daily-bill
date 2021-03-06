package org.daily.bill.controller;

import org.daily.bill.domain.Shop;
import org.daily.bill.domain.ShopView;
import org.daily.bill.response.Response;
import org.daily.bill.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.daily.bill.web.WebConstants.*;

/**
 * Created by vano on 14.8.16.
 */
@RestController
@RequestMapping("/shops")
@CrossOrigin("http://localhost:9000")
public class ShopRest {

    @Autowired
    private ShopService shopService;

    @RequestMapping(method = RequestMethod.PUT)
    public void create(@RequestBody Shop shop) {
        shopService.create(shop);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Response findById(@PathVariable Long id) {
        try {
            Shop shop = shopService.findById(id);
            return new Response(OK_CODE, OK_STATUS, "Shop", shop);
        } catch (Exception e) {
            return new Response(ERROR_CODE, ERROR_STATUS, e.getMessage(), e);
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public Response findAll() {
        try {
            List<Shop> shops = shopService.findAll();
            return new Response(OK_CODE, OK_STATUS, "Shop list", shops);
        } catch (Exception e) {
            return new Response(ERROR_CODE, ERROR_STATUS, e.getMessage(), e);
        }

    }

    @RequestMapping(method = RequestMethod.POST)
    public Response updateShop(@RequestBody Shop shop) {
        try {
            shopService.update(shop);
            return new Response(OK_CODE, OK_STATUS, "Updated");
        } catch (Exception e) {
            return new Response(ERROR_CODE, ERROR_STATUS, e.getMessage(), e);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value="/rating/calculate")
    public Response calculateShopRating() {
        try {
            shopService.calculateShopRating();
            return new Response(OK_CODE, OK_STATUS, "Rating isCalculated");
        } catch (Exception e) {
            return new Response(ERROR_CODE, ERROR_STATUS, e.getMessage(), e);
        }

    }
}
