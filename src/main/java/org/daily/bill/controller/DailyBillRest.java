package org.daily.bill.controller;

import org.daily.bill.domain.*;
import org.daily.bill.response.Response;
import org.daily.bill.service.DailyBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static org.daily.bill.web.WebConstants.*;

/**
 * Created by vano on 31.7.16.
 */
@RestController
@RequestMapping("/daily-bill")
@CrossOrigin("http://localhost:9000")
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

    @RequestMapping(value="/bills", method = RequestMethod.POST)
    public Response getBills(@RequestBody BillListParams params) {
        try {
            List<Bill> bills = dailyBillService.getBills(params);
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
            ClientStatisticsDetails details = dailyBillService.getDetailsByProduct(params);
            return new Response(OK_CODE, OK_STATUS, null, details);
        } catch (Exception e) {
            return new Response(ERROR_CODE, ERROR_STATUS, e.getMessage());
        }
    }
    @RequestMapping("/last/price/shop/{shopId}/product/{productId}")
    public Response findLastPrice(@PathVariable Long shopId, @PathVariable Long productId) {
        try {
            BigDecimal lastPrice = dailyBillService.findLastPrice(shopId, productId);
            return new Response(OK_CODE, OK_STATUS, null, lastPrice);
        } catch (Exception e) {
            return new Response(ERROR_CODE, ERROR_STATUS, e.getMessage());
        }
    }
}
