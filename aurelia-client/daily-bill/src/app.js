export class App {
 configureRouter(config, router){
    config.title = 'Daily Bill';
    config.map([
        { route: '',              moduleId: 'daily-bill/bill-list',   title: 'Bill List', name: 'billList'},
        { route: 'bill/:id',  moduleId: 'daily-bill/bill-details', name:'billDetails' },
        {route: 'bill/add', moduleId: 'daily-bill/add-bill', name: 'addBill'},
        {route: 'bill/edit/:id', moduleId: 'daily-bill/add-bill', name: 'editBill'},
        {route: 'statistics', moduleId: 'daily-bill/statistics', name: 'statistics'},
        {route: 'product/list', moduleId: 'daily-bill/product/product-list', name: 'product-list'},
        {route: 'shop/list', moduleId: 'daily-bill/shop/shop-list', name: 'shop-list'}
    ]);

    this.router = router;
  }
}
