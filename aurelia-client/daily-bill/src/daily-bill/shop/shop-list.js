import {DailyBillService} from "../service/daily-bill-service"
import {Router} from "aurelia-router"
export class ShopList {
    static inject() {
            return [DailyBillService, Router]
    }
    constructor(dailyBillService, router) {
        this.dailyBillService = dailyBillService;
        this.router = router;
        this.errorMsg = [];
        this.initShops();
    }

    initShops() {
        let self = this;
        this.dailyBillService.getAllShops()
            .then(response => response.json())
            .then(data => {
                self.shops = data.object;
            }).catch(e => {
                self.errorMsg.push(e)
            })
    }
}
