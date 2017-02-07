import {DailyBillService} from "./service/daily-bill-service"
import {json} from 'aurelia-fetch-client'
export class BillDetails {
static inject() {
    return [DailyBillService]
  }
  constructor(dailyBillService) {
    this.dailyBillService = dailyBillService;
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
      });
    }
}
