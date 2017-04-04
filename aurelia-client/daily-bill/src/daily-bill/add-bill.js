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
        this.products = [];
        this.shops = [];
    }
    activate(params, routeConfig) {
        this.routeConfig = routeConfig;
        console.log('activate add-bill');
        console.log('params: ', params.id)
        let self = this;

        this.dailyBillService.getShops().then(response => response.json())
                        .then(data => {
                            console.log('shops: ')
                            console.log(data)
                            self.shops = data.object;
                            self.dailyBillService.getProducts().then(response => response.json())
                                .then(prodData => {
                                    console.log('products: ')
                                    console.log(prodData);
                                    console.log(prodData.object);
                                    self.products = prodData.object})
                        })
        if(params.id) {
            let self = this;
            this.dailyBillService.getBillById(params.id)
                .then(response => response.json())
                .then(data => {
                    console.log('bill: ')
                    console.log(data.object)
                    self.bill = data.object;
                    self.initOpenBill();
                    console.log('bill date: ')
                    console.log(self.bill.date);
            });
        } else {
            this.createBill();
        }
    }
    initOpenBill() {
        let date = new Date(this.bill.date)
        this.bill.dateStr = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate()
    }
  createBill() {
    let date = new Date();
    this.bill = {
        id: null,
        date: date,
        dateStr: date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate(),
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
      amount: 1
    }
  }

  productChange(billItem) {
    console.log('Change product value:');
    let changedValue = billItem.selectedProduct;
    console.log(changedValue);
    console.log(changedValue.id + " " + changedValue.name);
    billItem.product.id = changedValue.id;
    billItem.product.name = changedValue.name;
  }

  shopChange() {
      console.log('Change shop value:');
      this.bill.shop.id = this.selectedShop.id;
      this.bill.shop.name = this.selectedShop.name;
  }

  shopNameChange() {
     console.log('shop name change')
     let shopName = this.bill.shop.name.toLowerCase();
     console.log('new: ' + shopName)
     let shop = this.shops.find((element, index, array) => element.name.toLowerCase() === shopName)
     if(shop) {
        this.bill.shop.id = shop.id;
        this.bill.shop.name = shop.name;
        this.selectedShop = shop;
     } else {
        this.bill.shop.id = null;
     }
  }

    productNameChange(billItem) {
        console.log('product name change')
        let productName = billItem.product.name.toLowerCase();
        console.log('bill item product')
        console.log(productName)
        let product = this.products.find((element, index, array) => element.name.toLowerCase() === productName)
        if(product) {
            billItem.product.id = product.id;
            billItem.product.name = product.name;
            billItem.selectedProduct = product;
        } else {
            billItem.product.id = null;
        }
    }
}
