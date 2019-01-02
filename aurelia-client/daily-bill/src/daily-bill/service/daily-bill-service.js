import {HttpClient, json} from 'aurelia-fetch-client'
import environment from '../../environment'

let httpClient = new HttpClient();

export class DailyBillService {
    constructor() {
        console.log('environment:' + environment.dailyBillUrl.origin);
    }

    getBills(params) {
        console.log("Get bills")
        return httpClient.fetch(environment.dailyBillUrl.origin + '/daily-bill/bills', {
                   method: "POST",
                   body: json(params)
            })
    }

    getBillById(id) {
        console.log("Get bill by id: " +id)
        return httpClient.fetch(environment.dailyBillUrl.origin + '/daily-bill/bill/' + id)
    }

    addBill(bill) {
        return httpClient.fetch(environment.dailyBillUrl.origin + '/daily-bill/add', {
             method: "PUT",
             body: json(bill)
        })
      }
    updateBill(bill) {
        return httpClient.fetch(environment.dailyBillUrl.origin + '/daily-bill/edit', {
               method: "POST",
               body: json(bill)
        })
    }

    getShops() {
        return httpClient.fetch(environment.dailyBillUrl.origin + '/daily-bill/shops')
    }

    getProducts() {
        return httpClient.fetch(environment.dailyBillUrl.origin + '/daily-bill/products')
    }

    getStatisticByProduct(params) {
        let url = environment.dailyBillUrl.origin + '/daily-bill/statistics/product';
        console.log("url: " + url);
        return httpClient.fetch(url, {
                   method: "POST",
                   body: json(params)
            })
    }

    getStatisticByShop(params) {
            let url = environment.dailyBillUrl.origin + '/daily-bill/statistics/shop';
            return httpClient.fetch(url, {
                       method: "POST",
                       body: json(params)
                })
        }

    findLastPrice(shopId, productId) {
        return httpClient.fetch(environment.dailyBillUrl.origin + '/daily-bill/last/price/shop/' + shopId + '/product/' + productId)
    }

    getAllShops(){
        return httpClient.fetch(environment.dailyBillUrl.origin + "/shops/all")
    }

    getShopById(id) {
        return httpClient.fetch(environment.dailyBillUrl.origin + "/shops/" + id)
    }

    updateShop(shop) {
        return httpClient.fetch(environment.dailyBillUrl.origin + "/shops", {
            method: "POST",
            body: json(shop)
        })
    }

    getAllProducts(){
        return httpClient.fetch(environment.dailyBillUrl.origin + "/products/all")
    }

    getProductById(id) {
        return httpClient.fetch(environment.dailyBillUrl.origin + "/products/" + id)
    }

    updateProduct(product) {
        return httpClient.fetch(environment.dailyBillUrl.origin + "/products", {
                method: "POST",
                body: json(product)
        })
    }

    getAllCurrencies() {
        return httpClient.fetch(environment.dailyBillUrl.origin + "/currency/all")
    }
}
