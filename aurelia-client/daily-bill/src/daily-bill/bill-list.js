import {DailyBillService} from "./service/daily-bill-service"
export class BillList {
  static inject() {
    return [DailyBillService]
  }
  constructor(dailyBillService) {
    this.dailyBillService = dailyBillService;
    this.localBillsbills = dailyBillService.getLocalBills();
  }

  activate(params, routeConfig) {
  var self = this;
    this.dailyBillService.getBills()
              .then(response => response.json())
              .then(data => {
                 console.log(data);
                 self.bills = data.object;
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
      var bill = this.dailyBillService.getBillById(id);
      console.log(bill);
      return bill;
    }
}
