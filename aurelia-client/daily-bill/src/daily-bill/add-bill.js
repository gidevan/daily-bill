import {DailyBillService} from "./service/daily-bill-service"
export class AddBill{
  static inject() {
    return [DailyBillService]
  }
  constructor(dailyBillService) {
    this.dailyBillService = dailyBillService;
    this.bill = {
      shopName: "",
      items: [
      ]
    };
    this.addBillItem()
  }

  addBillItem() {
    console.log("add bill item");
    this.bill.items.push(this.createDefaultBillItem());
  }

  addBill() {
    console.log("add bill:");
    console.log(this.bill);
  }

  createDefaultBillItem() {
    return {
      product: "",
      price: 0,
      countItem: 1
    }
  }
}
