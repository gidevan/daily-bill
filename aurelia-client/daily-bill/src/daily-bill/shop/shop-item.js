import {DailyBillService} from "../service/daily-bill-service"
import {Router} from "aurelia-router"

export class ShopItem {
    static inject() {
         return [DailyBillService, Router]
    }
    constructor(dailyBillService, router) {
        this.dailyBillService = dailyBillService;
        this.router = router;
        this.errorMessages = [];
    }

    activate(params, routeConfig) {
        let id = params.id;
        this.initShop(id)
    }

    initShop(id) {
        let self = this;
        this.dailyBillService.getShopById(id)
            .then(response => response.json())
            .then(data => {
                self.shop = data.object;
                console.log("shop:", self.shop)
            }).catch(e => {
                console.log("error get bill:" + e);
            })
    }

    updateShop() {
        this.errorMessages = [];
        let self = this;
        this.dailyBillService.updateShop(this.shop)
            .then(response => response.json())
            .then(data => {
                console.log("update shop result");
                console.log(data);
                if(data.code == '200'){
                    this.router.navigateToRoute('shop-list')
                } else {
                    self.errorMessages.push(data.message)
                }
            })
    }
}
