import {DailyBillService} from "./service/daily-bill-service"
import {Router} from "aurelia-router"

export class Statistics {
    static inject() {
        return [DailyBillService, Router]
    }

    constructor(dailyBillService, router) {
    console.log('Init statistics module')
        this.dailyBillService = dailyBillService;
        this.router = router;
        let date = new Date();
        this.params = {
            startPeriodDate: new Date(date.getFullYear(), date.getMonth(), 1),
            endPeriodDate: date
        }
    }

    activate(params, routeConfig) {
        let self = this;
        this.dailyBillService.getStatisticByProduct(this.params)
                .then(response => response.json())
                .then(data => {
                    console.log(data);
                    self.statisticsByProduct = data.object;
                });
    }
}
