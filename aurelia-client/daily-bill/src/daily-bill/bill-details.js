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
    this.message = "Bill details message";
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

            //self.bill.dateStr = new Date(data.object.date)
      });
    }
  edit() {
    console.log('edit bill: ',  this.bill.id)
    this.router.navigateToRoute("editBill", {id: this.bill.id});
  }
}
