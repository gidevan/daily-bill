import {DailyBillService} from "./service/daily-bill-service"
import {Router} from "aurelia-router"

export class StatisticsByShop {
    static inject() {
        return [DailyBillService, Router]
    }

    constructor(dailyBillService, router) {
        console.log('Init statistics by shop module')
        this.dailyBillService = dailyBillService;
        this.router = router;
        let date = new Date();
        this.params = {
            startPeriodDate: new Date(date.getFullYear(), date.getMonth(), 1),
            endPeriodDate: date
        }
        // Aurelia doesn't support change of array in repeat.for
        this.shopNames = [{name: ''}];
        this.startDateStr = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + 1;
        this.endDateStr = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
        this.selectedCurrency = null;
        this.currencies = [];
        this.fullStatiscicInfo = null;
        this.statisticsByShop = [];
    }

    activate(params, routeConfig) {
        this.getStatisticsByShop(this.params);
    }

    getStatisticsByShop(params) {
        let names = this.shopNames.map(it => it.name).filter(it => it);
        params.shopNames = names;
        console.log(params)
        this.statisticsByShop = {}
        let self = this;
        this.dailyBillService.getStatisticByShop(params)
                        .then(response => response.json())
                        .then(data => {
                            console.log(data);
                            self.fullStatisticInfo = data.object;
                            let currencies = self.fullStatisticInfo.currencies;
                            if (currencies.length > 0) {

                                self.currencies = currencies.map(it => {return {name: it, active: false}});
                                if(!self.selectedCurrency) {
                                    self.selectedCurrency = self.currencies[0].name;
                                    self.currencies[0].active = true;
                                } else {
                                    let isCurrencyExists = false;
                                    self.currencies.forEach(it => {
                                        if (it.name == self.selectedCurrency) {
                                            it.active = true;
                                            isCurrencyExists = true;
                                        }
                                    })
                                    if(!isCurrencyExists) {
                                        self.selectedCurrency = self.currencies[0].name;
                                        self.currencies[0].active = true;
                                    }
                                }


                                self.statisticsByShop = self.fullStatisticInfo.statisticInfo[self.selectedCurrency];
                                self.statisticsByShop.statisticDetails.map(it => {
                                                                it.active = true;
                                                                return it;
                                });

                                self.statisticsByShop.allEnabled = true;
                                self.statisticsByShop.totalSumCalculated = self.statisticsByShop.totalSum;
                            }
                        });
    }

    updateStatistics() {
        let startDate = Date.parse(this.startDateStr);
        let endDate = Date.parse(this.endDateStr);
        this.params = {
            startPeriodDate: startDate,
            endPeriodDate: endDate,
        }
        this.getStatisticsByShop(this.params);
    }

    changeCurrency(currency) {
        this.currencies.forEach(it => it.name != currency.name ? it.active = false : it.active = true);
        this.selectedCurrency = currency.name;
        this.updateStatistics();
    }

    switchItem(statisticsItem) {
        statisticsItem.active = !statisticsItem.active;
        this.statisticsByShop.totalSumCalculated = !statisticsItem.active
                ? this.statisticsByShop.totalSumCalculated - statisticsItem.price
                : this.statisticsByShop.totalSumCalculated + statisticsItem.price;
    }

    addShopName() {
        this.shopNames.push({name: ''});
    }

    switchItems() {
        this.statisticsByShop.allEnabled = !this.statisticsByShop.allEnabled;
        this.statisticsByShop.totalSumCalculated = !this.statisticsByShop.allEnabled ? 0 : this.statisticsByShop.totalSum;
        let self = this;
        this.statisticsByShop.statisticDetails.map(it => {
            it.active = self.statisticsByShop.allEnabled;
        })
    }
}
