import {DailyBillService} from "./service/daily-bill-service"
export class BillList {
  static inject() {
    return [DailyBillService]
  }
  constructor(dailyBillService) {
    this.dailyBillService = dailyBillService;
    this.bills = dailyBillService.getLocalBills();
  }

  viewBillDetails(id) {
    console.log("viewBillDetails: " + id);
    var bill = this.dailyBillService.getLocalBillById(id);
    console.log(bill);
    return bill;
  }
}
