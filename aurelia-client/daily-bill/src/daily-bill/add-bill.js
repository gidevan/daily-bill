import {DailyBillService} from "./service/daily-bill-service"
import {Router} from "aurelia-router"
export class AddBill{
    static inject() {
        return [DailyBillService, Router]
    }
    constructor(dailyBillService, router) {
        this.dailyBillService = dailyBillService;
        this.router = router;
    }
    activate(params, routeConfig) {
        this.routeConfig = routeConfig;
        console.log('activate add-bill');
        console.log('params: ', params.id)
        this.changeCurrency = false;
        let self =this;
        this.messages = [];
        this.dailyBillService.getShops()
            .then(response => response.json())
                        .then(data => {
                            console.log('shops: ')
                            console.log(data)
                            self.shops = data.object;
                            return self.shops;
                        }).then(shops => {
                            return self.dailyBillService.getProducts()


                        }).then(response => response.json())
                        .then(prodData => {
                                                              console.log('products: ')
                                                              console.log(prodData);
                                                              console.log(prodData.object);
                                                              self.products = prodData.object
                                                              return self.products;
                                                          })
                        .then(products => self.dailyBillService.getAllCurrencies())
                                                .then(response => response.json())
                                                .then(currenciesData => {
                                                    console.log('Currencies: ');
                                                    console.log(currenciesData);
                                                    self.currencies = currenciesData.object;

                                                })
                        .then(products => {
                            if(params.id) {
                                        return self.dailyBillService.getBillById(params.id)
                                            .then(response => response.json())
                                            .then(data => {
                                                console.log('bill: ')
                                                console.log(data.object)
                                                self.bill = data.object;
                                                self.initOpenBill();
                                                console.log('bill date: ')
                                                console.log(self.bill.date);
                                                return self.bill;
                                        });
                            } else {
                                this.createBill();
                            }

                        }).catch(error => {
                            console.log('Error getting data')
                            console.log(error)
                            self.shops = [];
                            self.products = [];
                        })

    }
    attached() {
        console.log('attached add-bill')
    }
    initOpenBill() {
        let date = new Date(this.bill.date)
        this.bill.dateStr = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate()
    }
  createBill() {
    let date = new Date();
    let defaultCurrency = this.currencies.find(c => c.defaultCurrency == true);
    console.log('defaultCurrency', defaultCurrency);
    this.bill = {
        id: null,
        date: date,
        dateStr: date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate(),
        currency: defaultCurrency,
        shop: {
          shopId: null,
          shopName: ""
        },
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
    this.validateBill();
    if(this.messages.length == 0) {
        console.log('bill to save:');
        console.log(this.bill);
        let self = this;
        this.dailyBillService.addBill(this.bill)
                .then(response => response.json())
                .then(data => {
                    console.log(data);
                    self.router.navigateToRoute('billList')
                });
    }

  }

  validateBill() {
  this.messages = [];
     if (!this.bill.shop.id) {
        this.messages.push("Shop is empty");
     }
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
      amount: 1
    }
  }
}
