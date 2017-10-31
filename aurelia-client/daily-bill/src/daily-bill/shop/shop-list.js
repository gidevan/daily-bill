import {DailyBillService} from "../service/daily-bill-service"
import {Router} from "aurelia-router"
export class ShopList {
    static inject() {
            return [DailyBillService, Router]
    }
    constructor(dailyBillService, router) {
        this.dailyBillService = dailyBillService;
        this.router = router;
    }
}
