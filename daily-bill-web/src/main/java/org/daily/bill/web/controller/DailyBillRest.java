package org.daily.bill.web.controller;

import org.daily.bill.api.service.DailyBillService;
import org.daily.bill.domain.Bill;
import org.daily.bill.domain.Shop;
import org.daily.bill.web.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by vano on 31.7.16.
 */
@RestController
@RequestMapping("/daily-bill")
public class DailyBillRest {

    private static final String ERROR_STATUS = "Error";
    private static final String OK_STATUS = "OK";
    private static final String ERROR_CODE = "500";
    private static final String OK_CODE = "200";

    @Autowired
    private DailyBillService dailyBillService;


    @RequestMapping("/info")
    public String info() {
        return "Daily bill app";
    }

    @RequestMapping("/shop/{id}")
    public Shop getShop(@PathVariable Long id) {
        return dailyBillService.getShopById(id);
    }

    @RequestMapping("/shops")
    public List<Shop> findShops() {
        return dailyBillService.findShops();
    }

    @RequestMapping(value = "/add", method = RequestMethod.PUT)
    public Response addBill(@RequestBody Bill bill) {
        try {
            Long billId = dailyBillService.addDailyBill(bill);
            if (billId != null) {
                return new Response(OK_CODE, OK_STATUS,  "Bill was stored", billId);
            } else {
                return new Response(ERROR_CODE, ERROR_STATUS, "Bill wasn't stored");
            }
        } catch (Exception e) {
            return new Response(ERROR_CODE, ERROR_STATUS, e.getMessage());
        }
    }

    @RequestMapping("/bills")
    public Response getBills() {
        try {
            List<Bill> bills = dailyBillService.getBills();
            return new Response(OK_CODE, OK_STATUS, null, bills);
        } catch (Exception e) {
            return new Response(ERROR_CODE, ERROR_STATUS, e.getMessage());
        }

    }
}
