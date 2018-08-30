import {DailyBillService} from "../service/daily-bill-service"
import {Router} from "aurelia-router"
export class CurrencyList {
    static inject() {
            return [DailyBillService, Router]
    }
    constructor(dailyBillService, router) {
        this.dailyBillService = dailyBillService;
        this.router = router;
        this.initCurrencies();
    }

    initCurrencies() {
        let self = this;
        this.currencies = [];
        this.dailyBillService.getAllCurrencies()
                .then(response => response.json())
                .then(data => {
                    self.currencies = data.object;
                }).catch(e => {
                    self.errorMessages.push(e)
                })
    }

    editCurrency(id) {
        console.log("edit currency: " + id);
    }


}
