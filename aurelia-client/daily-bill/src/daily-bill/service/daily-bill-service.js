import {HttpClient, json} from 'aurelia-fetch-client'
//import {HttpClient} from 'aurelia-http-client'

let httpClient = new HttpClient();

export class DailyBillService {
  constructor() {
    this.bills = [
      {id: 1, shop: "Shop1", date: "24-12-2016", price: 24.5},
      {id: 2, shop: "Shop2", date: "25-12-2016", price: 34.6},
      {id: 3, shop: "Shop1", date: "05-01-2017", price: 14.6},
      {id: 4, shop: "Shop3", date: "06-01-2017", price: 24.1},
    ]
  }

  getLocalBills() {
    return this.bills;
  }
  getLocalBillById(id) {
    console.log("service. get by id: " +id)
    for(let bill of this.bills) {
      if (bill.id == id) {
        return bill;
      }
    }
    return null;
  }

  getBills() {
    httpClient.fetch('http://localhost:8080/daily-bill/bills')
          .then(response => response.json())
          .then(data => {
             console.log(data);
          });
  }

  addBill(bill) {
    httpClient.fetch('http://localhost:8080/daily-bill/add', {
             method: "PUT",
             //body: JSON.stringify(bill)
             body: json(bill)
          })

          .then(response => response.json())
          .then(data => {
             console.log(data);
          });
  }
}
