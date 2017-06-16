import {DailyBillService} from "./service/daily-bill-service"
import {Router} from "aurelia-router"

export class Statistics {
    static inject() {
        return [DailyBillService, Router]
    }

    constructor(dailyBillService, router) {
    console.log('Init statistics module')
        this.dailyBillService = dailyBillService;
        this.router = router;
        let date = new Date();
        this.params = {
            startPeriodDate: new Date(date.getFullYear(), date.getMonth(), 1),
            endPeriodDate: date
        }
        // Aurelia doesn't support change of array in repeat.for
        this.productNames = [{name: ''}];
        this.startDateStr = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + 1;
        this.endDateStr = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
    }

    activate(params, routeConfig) {
        this.getStatisticsByProduct(this.params);
    }

    getStatisticsByProduct(params) {
        let names = this.productNames.map(it => it.name).filter(it => it);
        params.productNames = names;
        console.log(params)
        let self = this;
        this.dailyBillService.getStatisticByProduct(params)
                        .then(response => response.json())
                        .then(data => {
                            console.log(data);
                            self.statisticsByProduct = data.object;
                        });
    }

    updateStatistics() {
        let startDate = Date.parse(this.startDateStr);
        let endDate = Date.parse(this.endDateStr);
        this.params = {
            startPeriodDate: startDate,
            endPeriodDate: endDate,
        }
        this.getStatisticsByProduct(this.params);
    }

    addProductName() {
        this.productNames.push({name: ''});
    }
}
