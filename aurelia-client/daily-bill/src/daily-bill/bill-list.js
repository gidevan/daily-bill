import {DailyBillService} from "./service/daily-bill-service"
import {Router} from "aurelia-router"
export class BillList {
    static inject() {
        return [DailyBillService, Router]
    }
    constructor(dailyBillService, router) {
        this.dailyBillService = dailyBillService;
        this.router = router;
        let date = new Date();
        this.startDateStr = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + 1;
        this.endDateStr = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
    }

    activate(params, routeConfig) {
        this.findBills();
    }

    viewBillDetails(id) {
        console.log("viewBillDetails: " + id);
        this.router.navigateToRoute('billDetails', {id: id})
    }

    findBills() {
        let startDate = Date.parse(this.startDateStr);
        let endDate = Date.parse(this.endDateStr);
        this.params = {
            startPeriodDate: startDate,
            endPeriodDate: endDate,
        }
        var self = this;
        console.log('params get bill');
        console.log(this.params)
        this.dailyBillService.getBills(this.params)
                    .then(response => response.json())
                    .then(data => {
                        console.log(data);
                        self.bills = data.object;
                        self.bills.forEach((element) => {
                            let date = new Date(element.date);
                            element.dateStr = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
                        })
                    });
    }
}
