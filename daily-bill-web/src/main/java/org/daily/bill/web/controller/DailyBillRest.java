package org.daily.bill.web.controller;

import org.daily.bill.api.service.DailyBillService;
import org.daily.bill.domain.*;
import org.daily.bill.web.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * Created by vano on 31.7.16.
 */
@RestController
@RequestMapping("/daily-bill")
@CrossOrigin("http://localhost:9000")
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

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Response editBill(@RequestBody Bill bill) {
        try {
            Long billId = dailyBillService.addDailyBill(bill);
            if (billId != null) {
                return new Response(OK_CODE, OK_STATUS,  "Bill was updated", billId);
            } else {
                return new Response(ERROR_CODE, ERROR_STATUS, "Bill wasn't updated");
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

    @RequestMapping("/bill/{id}")
    public Response getBillById(@PathVariable Long id) {
        try {
            Bill bill = dailyBillService.getBillById(id);
            return new Response(OK_CODE, OK_STATUS, null, bill);
        } catch (Exception e) {
            return new Response(ERROR_CODE, ERROR_STATUS, e.getMessage());
        }

    }

    @RequestMapping("/products")
    public Response getProducts() {
        try {
            List<Product> products = dailyBillService.getProducts();
            return new Response(OK_CODE, OK_STATUS, null, products);
        } catch (Exception e) {
            return new Response(ERROR_CODE, ERROR_STATUS, e.getMessage());
        }
    }

    @RequestMapping("/shops")
    public Response findShops() {
        try {
            List<Shop> shops = dailyBillService.findShops();
            return new Response(OK_CODE, OK_STATUS, null, shops);
        } catch (Exception e) {
            return new Response(ERROR_CODE, ERROR_STATUS, e.getMessage());
        }
    }

    @RequestMapping(value="/statistics/product", method = RequestMethod.POST)
    public Response getStatisticsByProduct(@RequestBody StatisticsParams params) {
        try {
            List<StatisticDetails> details = dailyBillService.getDetailsByProduct(params);
            return new Response(OK_CODE, OK_STATUS, null, details);
        } catch (Exception e) {
            return new Response(ERROR_CODE, ERROR_STATUS, e.getMessage());
        }
    }
}
