import {HttpClient, json} from 'aurelia-fetch-client'
//import {HttpClient} from 'aurelia-http-client'

let httpClient = new HttpClient();

export class DailyBillService {
    constructor() {
    }

    getBills() {
        console.log("Get bills")
        return httpClient.fetch('http://localhost:8080/daily-bill/bills')
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
}
