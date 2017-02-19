export class App {
  configureRouter(config, router){
    config.title = 'Daily Bill';
    config.map([
      { route: '',              moduleId: 'daily-bill/bill-list',   title: 'Bill List', name: 'billList'},
      { route: 'bill/:id',  moduleId: 'daily-bill/bill-details', name:'billDetails' },
      {route: 'bill/add', moduleId: 'daily-bill/add-bill', name: 'addBill'},
      {route: 'bill/edit/:id', moduleId: 'daily-bill/add-bill', name: 'editBill'},
    ]);

    this.router = router;
  }
}
