export class App {
 configureRouter(config, router){
    config.title = 'Daily Bill';
    config.map([
        { route: '',              moduleId: 'daily-bill/bill-list',   title: 'Bill List', name: 'billList'},
        { route: 'bill/:id',  moduleId: 'daily-bill/bill-details', name:'billDetails' },
        {route: 'bill/add', moduleId: 'daily-bill/add-bill', name: 'addBill'},
        {route: 'bill/edit/:id', moduleId: 'daily-bill/add-bill', name: 'editBill'},
        {route: 'statistics-by-product', moduleId: 'daily-bill/statistics-by-product', name: 'statistics-by-product'},
        {route: 'statistics-by-shop', moduleId: 'daily-bill/statistics-by-shop', name: 'statistics-by-shop'},
        {route: 'product/list', moduleId: 'daily-bill/product/product-list', name: 'product-list'},
        {route: 'product/edit/:id', moduleId: 'daily-bill/product/product-item', name: 'product-item'},
        {route: 'shop/list', moduleId: 'daily-bill/shop/shop-list', name: 'shop-list'},
        {route: 'shop/edit/:id', moduleId: 'daily-bill/shop/shop-item', name: 'shop-item'},
        {route: 'currency/list', moduleId: 'daily-bill/currency/currency-list', name: 'currency-list'}
    ]);

    this.router = router;
  }
}
