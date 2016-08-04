package org.daily.bill.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by vano on 31.7.16.
 */
@RestController
@RequestMapping("/daily-bill")
public class DailyBillRest {

    @RequestMapping("/info")
    public String info() {
        return "Daily bill app";
    }
}
