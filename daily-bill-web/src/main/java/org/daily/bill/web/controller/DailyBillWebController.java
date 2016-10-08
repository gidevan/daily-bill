package org.daily.bill.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by vano on 12.9.16.
 */
@RequestMapping("/")
@Controller
public class DailyBillWebController {

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

}
