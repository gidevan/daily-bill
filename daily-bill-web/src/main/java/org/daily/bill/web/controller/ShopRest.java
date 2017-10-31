package org.daily.bill.web.controller;

import org.daily.bill.api.service.ShopService;
import org.daily.bill.domain.Shop;
import org.daily.bill.web.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.daily.bill.web.utils.WebConstants.OK_CODE;
import static org.daily.bill.web.utils.WebConstants.OK_STATUS;

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

    @RequestMapping(method = RequestMethod.GET)
    public Shop findById(@RequestParam Long id) {
        return shopService.findById(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public Response findAll() {
        List<Shop> shops = shopService.findAll();
        return new Response(OK_CODE, OK_STATUS, "Shop list", shops);
    }
}
