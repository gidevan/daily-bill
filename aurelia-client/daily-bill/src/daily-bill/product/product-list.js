import {DailyBillService} from "../service/daily-bill-service"
import {Router} from "aurelia-router"
export class ProductList {
    static inject() {
            return [DailyBillService, Router]
    }
    constructor(dailyBillService, router) {
        this.dailyBillService = dailyBillService;
        this.router = router;
        this.initProducts();
    }

    initProducts() {
        let self = this;
        this.errorMessages = [];
        this.products = [];
        this.dailyBillService.getAllProducts()
                .then(response => response.json())
                .then(data => {
                    self.products = data.object;
                }).catch(e => {
                    self.errorMessages.push(e)
                })
    }

    editProduct(id) {
        this.router.navigateToRoute('product-item', {id: id})
    }
}
