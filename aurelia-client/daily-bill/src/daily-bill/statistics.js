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
                            self.statisticsByProduct.statisticDetails.map(it => {
                                it.active = true;
                                return it;
                            });
                            self.statisticsByProduct.allEnabled = true;
                            self.statisticsByProduct.totalSumCalculated = self.statisticsByProduct.totalSum;
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

    switchItem(statisticsItem) {
        statisticsItem.active = !statisticsItem.active;
        this.statisticsByProduct.totalSumCalculated = !statisticsItem.active
                ? this.statisticsByProduct.totalSumCalculated - statisticsItem.price
                : this.statisticsByProduct.totalSumCalculated + statisticsItem.price;
    }

    addProductName() {
        this.productNames.push({name: ''});
    }

    switchItems() {
        this.statisticsByProduct.allEnabled = !this.statisticsByProduct.allEnabled;
        this.statisticsByProduct.totalSumCalculated = !this.statisticsByProduct.allEnabled ? 0 : this.statisticsByProduct.totalSum;
        let self = this;
        this.statisticsByProduct.statisticDetails.map(it => {
            it.active = self.statisticsByProduct.allEnabled;
        })
    }
}
