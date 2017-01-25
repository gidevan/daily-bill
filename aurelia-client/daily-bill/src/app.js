export class App {
  configureRouter(config, router){
    config.title = 'Daily Bill';
    config.map([
      { route: '',              moduleId: 'daily-bill/bill-list',   title: 'Bill List'},
      { route: 'bill/:id',  moduleId: 'daily-bill/bill-details', name:'billDetails' }
    ]);

    this.router = router;
  }
}
