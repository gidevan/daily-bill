import {DailyBillService} from "./service/daily-bill-service"
import {json} from 'aurelia-fetch-client'
import {Router} from "aurelia-router"
export class BillDetails {
    static inject() {
        return [DailyBillService, Router]
    }
    constructor(dailyBillService, router) {
        this.dailyBillService = dailyBillService;
        this.router = router;
    }

    activate(params, routeConfig) {
        this.routeConfig = routeConfig;
        var self = this;
        return this.dailyBillService.getBillById(params.id)
            .then(response => response.json())
            .then(data => {
                console.log('bill: ')
                console.log(data.object)
                self.bill = data.object;
                let date = new Date(data.object.date)
                self.bill.dateStr = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
                self.bill.billSum = 0;
                self.bill.items.forEach(element => {
                    self.bill.billSum += element.price * element.amount;
                })
          });
    }
    edit() {
        this.router.navigateToRoute("editBill", {id: this.bill.id});
    }
}
