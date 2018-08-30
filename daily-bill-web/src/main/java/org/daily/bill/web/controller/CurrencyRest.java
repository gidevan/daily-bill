package org.daily.bill.web.controller;

import org.daily.bill.api.service.CurrencyService;
import org.daily.bill.domain.Currency;
import org.daily.bill.domain.Shop;
import org.daily.bill.web.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.daily.bill.web.utils.WebConstants.*;
import static org.daily.bill.web.utils.WebConstants.ERROR_CODE;
import static org.daily.bill.web.utils.WebConstants.ERROR_STATUS;

/**
 * Created by vano on 31.8.18.
 */
@RestController
@RequestMapping("/currency")
@CrossOrigin("http://localhost:9000")
public class CurrencyRest {

    @Autowired
    private CurrencyService currencyService;

    @RequestMapping(method = RequestMethod.PUT)
    public void create(@RequestBody Currency currency) {
        currencyService.create(currency);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Response findById(@PathVariable Long id) {
        try {
            Currency currency = currencyService.findById(id);
            return new Response(OK_CODE, OK_STATUS, "Shop", currency);
        } catch (Exception e) {
            return new Response(ERROR_CODE, ERROR_STATUS, e.getMessage(), e);
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public Response findAll() {
        try {
            List<Currency> currencies = currencyService.findAll();
            return new Response(OK_CODE, OK_STATUS, "Shop list", currencies);
        } catch (Exception e) {
            return new Response(ERROR_CODE, ERROR_STATUS, e.getMessage(), e);
        }

    }

    @RequestMapping(method = RequestMethod.POST)
    public Response updateCurrency(@RequestBody Currency currency) {
        try {
            currencyService.update(currency);
            return new Response(OK_CODE, OK_STATUS, "Updated");
        } catch (Exception e) {
            return new Response(ERROR_CODE, ERROR_STATUS, e.getMessage(), e);
        }

    }
}
