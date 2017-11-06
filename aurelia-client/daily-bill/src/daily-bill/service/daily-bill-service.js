import {HttpClient, json} from 'aurelia-fetch-client'
//import {HttpClient} from 'aurelia-http-client'

let httpClient = new HttpClient();

export class DailyBillService {
    constructor() {
    }

    getBills(params) {
        console.log("Get bills")
        return httpClient.fetch('http://localhost:8080/daily-bill/bills', {
                   method: "POST",
                   body: json(params)
            })
    }

    getBillById(id) {
        console.log("Get bill by id: " +id)
        return httpClient.fetch('http://localhost:8080/daily-bill/bill/' + id)
    }

    addBill(bill) {
        return httpClient.fetch('http://localhost:8080/daily-bill/add', {
             method: "PUT",
             body: json(bill)
        })
      }
    updateBill(bill) {
        return httpClient.fetch('http://localhost:8080/daily-bill/edit', {
               method: "POST",
               body: json(bill)
        })
    }

    getShops() {
        return httpClient.fetch('http://localhost:8080/daily-bill/shops')
    }

    getProducts() {
        return httpClient.fetch('http://localhost:8080/daily-bill/products')
    }

    getStatisticByProduct(params) {
        console.log('Getstatistic by product');
        return httpClient.fetch('http://localhost:8080/daily-bill/statistics/product', {
                   method: "POST",
                   body: json(params)
            })
    }

    findLastPrice(shopId, productId) {
        return httpClient.fetch('http://localhost:8080/daily-bill/last/price/shop/' + shopId + '/product/' + productId)
    }

    getAllShops(){
        return httpClient.fetch("http://localhost:8080/shops/all")
    }

    getShopById(id) {
        return httpClient.fetch("http://localhost:8080/shops/" + id)
    }

    updateShop(shop) {
        return httpClient.fetch("http://localhost:8080/shops", {
            method: "POST",
            body: json(shop)
        })
    }

    getAllProducts(){
        return httpClient.fetch("http://localhost:8080/products/all")
    }

    getProductById(id) {
        return httpClient.fetch("http://localhost:8080/products/" + id)
    }

    updateProduct(product) {
        return httpClient.fetch("http://localhost:8080/products", {
                method: "POST",
                body: json(product)
        })
    }
}
