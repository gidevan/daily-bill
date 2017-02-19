import {DailyBillService} from "./service/daily-bill-service"
import {Router} from "aurelia-router"
export class AddBill{
  static inject() {
    return [DailyBillService, Router]
  }
  constructor(dailyBillService, router) {
    this.dailyBillService = dailyBillService;
    this.router = router;
    this.createBill();
  }
  activate(params, routeConfig) {
    this.routeConfig = routeConfig;
    console.log('activate add-bill');
    console.log('params: ', params.id)
    if(params.id) {
      let self = this;
      this.dailyBillService.getBillById(params.id)
                .then(response => response.json())
                .then(data => {
                  console.log('bill: ')
                  console.log(data.object)
                  self.bill = data.object;
            });
    } else {
      this.createBill();
    }
  }

  createBill() {
    this.bill = {
        id: null,
        shop: {
          shopId: null,
          shopName: ""
        },
        date: null,
        dateStr: '',
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
    this.bill.date = Date.parse(this.bill.dateStr);
    console.log(this.bill);
    let self = this;
    this.dailyBillService.addBill(this.bill)
        .then(response => response.json())
        .then(data => {
            console.log(data);
            self.router.navigateToRoute('billList')
        });;
  }

  updateBill() {
      console.log("update bill:");
      this.bill.date = Date.parse(this.bill.dateStr);
      console.log(this.bill);
      let self = this;
      this.dailyBillService.updateBill(this.bill)
          .then(response => response.json())
          .then(data => {
              console.log(data);
              self.router.navigateToRoute('billList')
          });;
    }

  createDefaultBillItem() {
    return {
      product: {
        productId: null,
        productName: null
      },
      price: 0,
      countItem: 1
    }
  }
}
