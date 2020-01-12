package org.daily.bill;

import org.daily.bill.service.StartupService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;


/**
 * Created by vano on 31.7.16.
 */
@SpringBootApplication
public class DailyBillApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(DailyBillApplication.class, args);
        init(ctx);
    }

    private static void init(ApplicationContext ctx) {
        StartupService startupService = ctx.getBean(StartupService.class);
        startupService.startup();
    }

}
