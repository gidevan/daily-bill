import {bindable, bindingMode} from 'aurelia-framework';
import {DailyBillService} from "../../service/daily-bill-service"
export class EditBillItem {

    @bindable billItem;
    @bindable products;
    @bindable shopId;
    static inject() {
        return [DailyBillService]
    }
    constructor(dailyBillService) {
        console.log("add bill item constructor")
        this.dailyBillService = dailyBillService;
    }

    findLastPrice = (productId) => {
        console.log('bill-item: find last price: ' + productId);
        console.log(this.shopId);
        if(this.shopId) {
            let self = this;
            return this.dailyBillService.findLastPrice(this.shopId, productId)
                          .then(response => response.json())
                          .then(data => {
                              console.log('last price')
                              console.log(data);
                              let lastPrice = data.object;
                              self.billItem.price = lastPrice ? lastPrice : 0;
                              return data;
                          });
        }

    }
}
