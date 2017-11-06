import {DailyBillService} from "../service/daily-bill-service"
import {Router} from "aurelia-router"

export class ProductItem {
    static inject() {
         return [DailyBillService, Router]
    }
    constructor(dailyBillService, router) {
        this.dailyBillService = dailyBillService;
        this.router = router;

    }

    activate(params, routeConfig) {
        let id = params.id;
        this.initProduct(id)
    }

    initProduct(id) {
        this.errorMessages = [];
        let self = this;
        this.dailyBillService.getProductById(id)
            .then(response => response.json())
            .then(data => {
                self.product = data.object;
                console.log("product:", self.product)
            }).catch(e => {
                console.log("error get bill:" + e);
            })
    }

    updateProduct() {
        this.errorMessages = [];
        let self = this;
        this.dailyBillService.updateProduct(this.product)
            .then(response => response.json())
            .then(data => {
                console.log("update product result");
                console.log(data);
                if(data.code == '200'){
                    this.router.navigateToRoute('product-list')
                } else {
                    self.errorMessages.push(data.message)
                }
            })
    }
}
