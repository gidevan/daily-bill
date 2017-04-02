import {DailyBillService} from "./service/daily-bill-service"
import {Router} from "aurelia-router"
export class BillList {
  static inject() {
    return [DailyBillService, Router]
  }
  constructor(dailyBillService, router) {
    this.dailyBillService = dailyBillService;
    this.router = router;
    this.localBillsbills = dailyBillService.getLocalBills();
  }

  activate(params, routeConfig) {
  var self = this;
    this.dailyBillService.getBills()
              .then(response => response.json())
              .then(data => {
                 console.log(data);
                 self.bills = data.object;
                 self.bills.forEach((element) => {
                    let date = new Date(element.date);
                    element.dateStr = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
                 })
              });
  }

  viewLocalBillDetails(id) {
    console.log("viewBillDetails: " + id);
    var bill = this.dailyBillService.getLocalBillById(id);
    console.log(bill);
    return bill;
  }

  viewBillDetails(id) {
      console.log("viewBillDetails: " + id);
      this.router.navigateToRoute('billDetails', {id: id})

    }
}
