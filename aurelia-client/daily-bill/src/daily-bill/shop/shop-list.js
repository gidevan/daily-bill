import {DailyBillService} from "../service/daily-bill-service"
import {Router} from "aurelia-router"
export class ShopList {
    static inject() {
            return [DailyBillService, Router]
    }
    constructor(dailyBillService, router) {
        this.dailyBillService = dailyBillService;
        this.router = router;
        this.initShops();
        this.filterValue = "";
    }

    initShops() {
        let self = this;
        this.errorMessages = [];
        this.dailyBillService.getAllShops()
            .then(response => response.json())
            .then(data => {
                self.shops = data.object;
                self.filteredShops = data.object;
            }).catch(e => {
                self.errorMessages.push(e)
            })
    }

    editShop(id) {
        this.router.navigateToRoute('shop-item', {id: id})
    }

    filterChange() {
        console.log("filter shop changed")
        if(this.filterValue) {
            this.filteredShops = this.shops.filter(it => it.name.toLowerCase().indexOf(this.filterValue.toLowerCase()) >= 0);
        } else {
            this.filteredShops = this.shops;
        }
    }
}
