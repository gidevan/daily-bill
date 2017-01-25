export class DailyBillService {
  constructor() {
    this.bills = [
      {id: 1, shop: "Shop1", date: "24-12-2016", price: 24.5},
      {id: 2, shop: "Shop2", date: "25-12-2016", price: 34.6},
      {id: 3, shop: "Shop1", date: "05-01-2017", price: 14.6},
      {id: 4, shop: "Shop3", date: "06-01-2017", price: 24.1},
    ]
  }

  getBills() {
    return this.bills;
  }
  getBillById(id) {
    console.log("service. get by id: " +id)
    for(let bill of this.bills) {
      if (bill.id == id) {
        return bill;
      }
    }
    return null;
  }
}
