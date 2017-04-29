define('app',['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  var App = exports.App = function () {
    function App() {
      _classCallCheck(this, App);
    }

    App.prototype.configureRouter = function configureRouter(config, router) {
      config.title = 'Daily Bill';
      config.map([{ route: '', moduleId: 'daily-bill/bill-list', title: 'Bill List', name: 'billList' }, { route: 'bill/:id', moduleId: 'daily-bill/bill-details', name: 'billDetails' }, { route: 'bill/add', moduleId: 'daily-bill/add-bill', name: 'addBill' }, { route: 'bill/edit/:id', moduleId: 'daily-bill/add-bill', name: 'editBill' }, { route: 'statistics', moduleId: 'daily-bill/statistics', name: 'statistics' }]);

      this.router = router;
    };

    return App;
  }();
});
define('environment',["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = {
    debug: true,
    testing: true
  };
});
define('main',['exports', './environment'], function (exports, _environment) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.configure = configure;

  var _environment2 = _interopRequireDefault(_environment);

  function _interopRequireDefault(obj) {
    return obj && obj.__esModule ? obj : {
      default: obj
    };
  }

  Promise.config({
    warnings: {
      wForgottenReturn: false
    }
  });

  function configure(aurelia) {
    aurelia.use.standardConfiguration().feature('resources');

    if (_environment2.default.debug) {
      aurelia.use.developmentLogging();
    }

    if (_environment2.default.testing) {
      aurelia.use.plugin('aurelia-testing');
    }

    aurelia.start().then(function () {
      return aurelia.setRoot();
    });
  }
});
define('daily-bill/add-bill',["exports", "./service/daily-bill-service", "aurelia-router"], function (exports, _dailyBillService, _aureliaRouter) {
    "use strict";

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.AddBill = undefined;

    function _classCallCheck(instance, Constructor) {
        if (!(instance instanceof Constructor)) {
            throw new TypeError("Cannot call a class as a function");
        }
    }

    var AddBill = exports.AddBill = function () {
        AddBill.inject = function inject() {
            return [_dailyBillService.DailyBillService, _aureliaRouter.Router];
        };

        function AddBill(dailyBillService, router) {
            _classCallCheck(this, AddBill);

            this.dailyBillService = dailyBillService;
            this.router = router;
            this.createBill();
            this.products = [];
            this.shops = [];
        }

        AddBill.prototype.activate = function activate(params, routeConfig) {
            var _this = this;

            this.routeConfig = routeConfig;
            console.log('activate add-bill');
            console.log('params: ', params.id);
            var self = this;

            this.dailyBillService.getShops().then(function (response) {
                return response.json();
            }).then(function (data) {
                console.log('shops: ');
                console.log(data);
                self.shops = data.object;
                self.dailyBillService.getProducts().then(function (response) {
                    return response.json();
                }).then(function (prodData) {
                    console.log('products: ');
                    console.log(prodData);
                    console.log(prodData.object);
                    self.products = prodData.object;
                });
            });
            if (params.id) {
                (function () {
                    var self = _this;
                    _this.dailyBillService.getBillById(params.id).then(function (response) {
                        return response.json();
                    }).then(function (data) {
                        console.log('bill: ');
                        console.log(data.object);
                        self.bill = data.object;
                        self.initOpenBill();
                        console.log('bill date: ');
                        console.log(self.bill.date);
                    });
                })();
            } else {
                this.createBill();
            }
        };

        AddBill.prototype.initOpenBill = function initOpenBill() {
            var date = new Date(this.bill.date);
            this.bill.dateStr = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
        };

        AddBill.prototype.createBill = function createBill() {
            var date = new Date();
            this.bill = {
                id: null,
                date: date,
                dateStr: date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate(),
                shop: {
                    shopId: null,
                    shopName: ""
                },
                items: []
            };
            this.addBillItem();
        };

        AddBill.prototype.addBillItem = function addBillItem() {
            console.log("add bill item");
            this.bill.items.push(this.createDefaultBillItem());
        };

        AddBill.prototype.addBill = function addBill() {
            console.log("add bill:");
            this.bill.date = Date.parse(this.bill.dateStr);
            console.log(this.bill);
            var self = this;
            this.dailyBillService.addBill(this.bill).then(function (response) {
                return response.json();
            }).then(function (data) {
                console.log(data);
                self.router.navigateToRoute('billList');
            });;
        };

        AddBill.prototype.updateBill = function updateBill() {
            console.log("update bill:");
            this.bill.date = Date.parse(this.bill.dateStr);
            console.log(this.bill);
            var self = this;
            this.dailyBillService.updateBill(this.bill).then(function (response) {
                return response.json();
            }).then(function (data) {
                console.log(data);
                self.router.navigateToRoute('billList');
            });;
        };

        AddBill.prototype.createDefaultBillItem = function createDefaultBillItem() {
            return {
                product: {
                    productId: null,
                    productName: null
                },
                price: 0,
                amount: 1
            };
        };

        AddBill.prototype.productChange = function productChange(billItem) {
            console.log('Change product value:');
            var changedValue = billItem.selectedProduct;
            console.log(changedValue);
            console.log(changedValue.id + " " + changedValue.name);
            billItem.product.id = changedValue.id;
            billItem.product.name = changedValue.name;
        };

        AddBill.prototype.shopChange = function shopChange() {
            console.log('Change shop value:');
            this.bill.shop.id = this.selectedShop.id;
            this.bill.shop.name = this.selectedShop.name;
        };

        AddBill.prototype.shopNameChange = function shopNameChange() {
            console.log('shop name change');
            var shopName = this.bill.shop.name.toLowerCase();
            console.log('new: ' + shopName);
            var shop = this.shops.find(function (element, index, array) {
                return element.name.toLowerCase() === shopName;
            });
            if (shop) {
                this.bill.shop.id = shop.id;
                this.bill.shop.name = shop.name;
                this.selectedShop = shop;
            } else {
                this.bill.shop.id = null;
            }
        };

        AddBill.prototype.productNameChange = function productNameChange(billItem) {
            console.log('product name change');
            var productName = billItem.product.name.toLowerCase();
            console.log('bill item product');
            console.log(productName);
            var product = this.products.find(function (element, index, array) {
                return element.name.toLowerCase() === productName;
            });
            if (product) {
                billItem.product.id = product.id;
                billItem.product.name = product.name;
                billItem.selectedProduct = product;
            } else {
                billItem.product.id = null;
            }
        };

        return AddBill;
    }();
});
define('daily-bill/bill-details',["exports", "./service/daily-bill-service", "aurelia-fetch-client", "aurelia-router"], function (exports, _dailyBillService, _aureliaFetchClient, _aureliaRouter) {
    "use strict";

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.BillDetails = undefined;

    function _classCallCheck(instance, Constructor) {
        if (!(instance instanceof Constructor)) {
            throw new TypeError("Cannot call a class as a function");
        }
    }

    var BillDetails = exports.BillDetails = function () {
        BillDetails.inject = function inject() {
            return [_dailyBillService.DailyBillService, _aureliaRouter.Router];
        };

        function BillDetails(dailyBillService, router) {
            _classCallCheck(this, BillDetails);

            this.dailyBillService = dailyBillService;
            this.router = router;
        }

        BillDetails.prototype.activate = function activate(params, routeConfig) {
            this.routeConfig = routeConfig;
            var self = this;
            return this.dailyBillService.getBillById(params.id).then(function (response) {
                return response.json();
            }).then(function (data) {
                console.log('bill: ');
                console.log(data.object);
                self.bill = data.object;
                var date = new Date(data.object.date);
                self.bill.dateStr = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
                self.bill.billSum = 0;
                self.bill.items.forEach(function (element) {
                    self.bill.billSum += element.price * element.amount;
                });
            });
        };

        BillDetails.prototype.edit = function edit() {
            this.router.navigateToRoute("editBill", { id: this.bill.id });
        };

        return BillDetails;
    }();
});
define('daily-bill/bill-list',["exports", "./service/daily-bill-service", "aurelia-router"], function (exports, _dailyBillService, _aureliaRouter) {
    "use strict";

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.BillList = undefined;

    function _classCallCheck(instance, Constructor) {
        if (!(instance instanceof Constructor)) {
            throw new TypeError("Cannot call a class as a function");
        }
    }

    var BillList = exports.BillList = function () {
        BillList.inject = function inject() {
            return [_dailyBillService.DailyBillService, _aureliaRouter.Router];
        };

        function BillList(dailyBillService, router) {
            _classCallCheck(this, BillList);

            this.dailyBillService = dailyBillService;
            this.router = router;
        }

        BillList.prototype.activate = function activate(params, routeConfig) {
            var self = this;
            this.dailyBillService.getBills().then(function (response) {
                return response.json();
            }).then(function (data) {
                console.log(data);
                self.bills = data.object;
                self.bills.forEach(function (element) {
                    var date = new Date(element.date);
                    element.dateStr = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
                });
            });
        };

        BillList.prototype.viewBillDetails = function viewBillDetails(id) {
            console.log("viewBillDetails: " + id);
            this.router.navigateToRoute('billDetails', { id: id });
        };

        return BillList;
    }();
});
define('daily-bill/statistics',["exports", "./service/daily-bill-service", "aurelia-router"], function (exports, _dailyBillService, _aureliaRouter) {
    "use strict";

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.Statistics = undefined;

    function _classCallCheck(instance, Constructor) {
        if (!(instance instanceof Constructor)) {
            throw new TypeError("Cannot call a class as a function");
        }
    }

    var Statistics = exports.Statistics = function () {
        Statistics.inject = function inject() {
            return [_dailyBillService.DailyBillService, _aureliaRouter.Router];
        };

        function Statistics(dailyBillService, router) {
            _classCallCheck(this, Statistics);

            console.log('Init statistics module');
            this.dailyBillService = dailyBillService;
            this.router = router;
            var date = new Date();
            this.params = {
                startPeriodDate: new Date(date.getFullYear(), date.getMonth(), 1),
                endPeriodDate: date
            };
            this.startDateStr = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + 1;
            this.endDateStr = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
        }

        Statistics.prototype.activate = function activate(params, routeConfig) {
            this.getStatisticsByProduct(this.params);
        };

        Statistics.prototype.getStatisticsByProduct = function getStatisticsByProduct(params) {
            console.log('get statistics');
            console.log(params);
            var self = this;
            this.dailyBillService.getStatisticByProduct(params).then(function (response) {
                return response.json();
            }).then(function (data) {
                console.log(data);
                self.statisticsByProduct = data.object;
            });
        };

        Statistics.prototype.updateStatistics = function updateStatistics() {
            var startDate = Date.parse(this.startDateStr);
            var endDate = Date.parse(this.endDateStr);
            console.log('update statistics');
            console.log(startDate, endDate);
            this.params = {
                startPeriodDate: startDate,
                endPeriodDate: endDate,
                productName: this.productName
            };
            this.getStatisticsByProduct(this.params);
        };

        return Statistics;
    }();
});
define('resources/index',["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.configure = configure;
  function configure(config) {}
});
define('daily-bill/service/daily-bill-service',['exports', 'aurelia-fetch-client'], function (exports, _aureliaFetchClient) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.DailyBillService = undefined;

    function _classCallCheck(instance, Constructor) {
        if (!(instance instanceof Constructor)) {
            throw new TypeError("Cannot call a class as a function");
        }
    }

    var httpClient = new _aureliaFetchClient.HttpClient();

    var DailyBillService = exports.DailyBillService = function () {
        function DailyBillService() {
            _classCallCheck(this, DailyBillService);
        }

        DailyBillService.prototype.getBills = function getBills() {
            console.log("Get bills");
            return httpClient.fetch('http://localhost:8080/daily-bill/bills');
        };

        DailyBillService.prototype.getBillById = function getBillById(id) {
            console.log("Get bill by id: " + id);
            return httpClient.fetch('http://localhost:8080/daily-bill/bill/' + id);
        };

        DailyBillService.prototype.addBill = function addBill(bill) {
            return httpClient.fetch('http://localhost:8080/daily-bill/add', {
                method: "PUT",
                body: (0, _aureliaFetchClient.json)(bill)
            });
        };

        DailyBillService.prototype.updateBill = function updateBill(bill) {
            return httpClient.fetch('http://localhost:8080/daily-bill/edit', {
                method: "POST",
                body: (0, _aureliaFetchClient.json)(bill)
            });
        };

        DailyBillService.prototype.getShops = function getShops() {
            return httpClient.fetch('http://localhost:8080/daily-bill/shops');
        };

        DailyBillService.prototype.getProducts = function getProducts() {
            return httpClient.fetch('http://localhost:8080/daily-bill/products');
        };

        DailyBillService.prototype.getStatisticByProduct = function getStatisticByProduct(params) {
            console.log('Getstatistic by product');
            return httpClient.fetch('http://localhost:8080/daily-bill/statistics/product', {
                method: "POST",
                body: (0, _aureliaFetchClient.json)(params)
            });
        };

        return DailyBillService;
    }();
});
define('text!app.html', ['module'], function(module) { module.exports = "<template><require from=\"./css/daily-bill.css\"></require><nav role=\"navigation\"><div><a href=\"#\"><span>Bills</span> </a><span>|</span> <a route-href=\"route: addBill\"><span>Add Bill</span> </a><span>|</span> <a route-href=\"route: statistics\"><span>Statistics</span></a></div></nav><div class=\"container\"><router-view></router-view></div></template>"; });
define('text!css/daily-bill.css', ['module'], function(module) { module.exports = "\n.bill-list .bill {\n  border: 1px solid black;\n  margin: 1px;\n}\n\n.bill-list .bill .bill-info>span {\n  font-weight: bolder;\n}\n\n.shop-info {\n  display: flex;\n  flex-direction: row;\n}\n.item{\n  border: 1px solid black;\n  margin: 2px;\n}\n\n.bill-item-info {\n  display: flex;\n  flex-direction: row;\n}\n\n.shop-info > p, .bill-item-info >p{\n  font-weight: bolder;\n  width: 20%;\n}\n\n.bill-item {\n  display: flex;\n  flex-direction: row;\n  border: 1px solid black;\n  margin: 1px;\n  padding: 1px;\n}\n.bill-item .item-description {\n  font-weight: bolder;\n  margin-right: 2px;\n  margin-left: 2px;\n  width: 20%;\n}\n.item-button-panel button {\n    width: 100%;\n    font-size: 24px;\n    margin: 2px;\n}\n.bill-button-panel button {\n  width: 100%;\n  font-size: 24px;\n  margin: 2px;\n}\n\n.bill-details {\n    border: 1px solid black;\n}\n\n.bill-details .bill-info {\n    margin: 2px;\n    padding-bottom: 3px;\n    font-size: 20px;\n\n}\n\n.bill-details .bill-info .bill-info-item {\n    display: flex;\n    flex-direction: row;\n}\n\n.bill-details .bill-info .bill-info-item .bill-info-description {\n    font-weight: bolder;\n    width: 20%;\n}\n.bill-items .bill-item {\n    display: flex;\n    flex-direction: column;\n}\n\n.bill-items .bill-item .bill-item-info-data  {\n    display: flex;\n    flex-direction: row;\n}\n\n.statistics-item {\n    border: 1px solid black;\n    margin: 2px;\n    padding: 2px;\n    display: flex;\n    flex-direction: row;\n}\n\n.statistics-item .name {\n    font-weight: bolder;\n}\n\n.statistics-button-panel {\n    display: flex;\n    flex-direction: row;\n}\n\n.statistics-button-panel > div {\n    display: flex;\n    flex-direction: row;\n}\n\n.statistics-button-panel .filter-field {\n    margin: 2px;\n}\n\n.statistics-button-panel .filter-field .filter-title {\n    font-weight: bolder;\n}\n\n.total-sum {\n    display: flex;\n    flex-direction: row;\n    margin: 3px;\n}\n\n.total-sum .title {\n    font-weight: bolder;\n    margin-right: 5px;\n}\n\n\n\n"; });
define('text!daily-bill/add-bill.html', ['module'], function(module) { module.exports = "<template><h1>Add Bill</h1><div class=\"shop-info\"><p>Shop name:</p><input type=\"text\" value.bind=\"bill.shop.name\" change.delegate=\"shopNameChange()\"><select value.bind=\"selectedShop\" change.delegate=\"shopChange()\"><option model.bind=\"null\">Choose...</option><option repeat.for=\"shop of shops\" model.bind=\"shop\">${shop.name}</option></select></div><div class=\"shop-info\"><p>Date:</p><input type=\"text\" value.bind=\"bill.dateStr\"></div><div class=\"bill-items\"><div class=\"item\" repeat.for=\"billItem of bill.items\"><div class=\"bill-item-info\"><p>Product:</p><input type=\"text\" value.bind=\"billItem.product.name\" change.delegate=\"productNameChange(billItem)\"><select value.bind=\"billItem.selectedProduct\" change.delegate=\"productChange(billItem)\"><option model.bind=\"null\">Choose...</option><option repeat.for=\"product of products\" model.bind=\"product\">${product.name}</option></select></div><div class=\"bill-item-info\"><p>Price:</p><input type=\"text\" value.bind=\"billItem.price\"></div><div class=\"bill-item-info\"><p>Amount:</p><input type=\"text\" value.bind=\"billItem.amount\"></div></div><div class=\"item-button-panel\"><button type=\"button\" click.delegate=\"addBillItem()\">Add item</button></div></div><div class=\"bill-button-panel\" if.bind=\"!bill.id\"><button type=\"button\" click.delegate=\"addBill()\">Add bill</button></div><div class=\"bill-button-panel\" if.bind=\"bill.id\"><button type=\"button\" click.delegate=\"updateBill()\">Update bill</button></div></template>"; });
define('text!daily-bill/bill-details.html', ['module'], function(module) { module.exports = "<template><div class=\"bill-details\"><div class=\"bill-info\"><div class=\"bill-info-item\"><div class=\"bill-info-description\">bill id:</div><div class=\"bill-info-item\">${bill.id}</div></div><div class=\"bill-info-item\"><div class=\"bill-info-description\">date:</div><div class=\"bill-info-item\">${bill.dateStr}</div></div><div class=\"bill-info-item\"><div class=\"bill-info-description\">shop id:</div><div class=\"bill-info-item\">${bill.shop.id}</div></div><div class=\"bill-info-item\"><div class=\"bill-info-description\">shop:</div><div class=\"bill-info-item\">${bill.shop.name}</div></div><div class=\"bill-info-item\"><div class=\"bill-info-description\">bill sum:</div><div class=\"bill-info-item\">${bill.billSum}</div></div></div><div class=\"bill-items\"><div class=\"bill-item\" repeat.for=\"item of bill.items\"><div class=\"bill-item-info-data\"><div class=\"item-description\">product id:</div><div class=\"item-value\">${item.product.id}</div></div><div class=\"bill-item-info-data\"><div class=\"item-description\">product:</div><div class=\"item-value\">${item.product.name}</div></div><div class=\"bill-item-info-data\"><div class=\"item-description\">price:</div><div class=\"item-value\">${item.price}</div></div><div class=\"bill-item-info-data\"><div class=\"item-description\">amount:</div><div class=\"item-value\">${item.amount}</div></div><div class=\"bill-item-info-data\"><div class=\"item-description\">sum:</div><div class=\"item-value\">${item.amount * item.price}</div></div></div></div></div><div class=\"bill-button-panel\"><button class=\"edit-bill-button\" click.delegate=\"edit()\">Edit bill</button></div></template>"; });
define('text!daily-bill/bill-list.html', ['module'], function(module) { module.exports = "<template><h1>Bill List</h1><div class=\"bill-list\"><div repeat.for=\"bill of bills\" class=\"bill\" click.delegate=\"viewBillDetails(bill.id)\"><div class=\"bill-info\"><span>id:</span> ${bill.id}</div><div class=\"bill-info\"><span>shop:</span> ${bill.shopName}</div><div class=\"bill-info\"><span>date:</span> ${bill.dateStr}</div><div class=\"bill-info\"><span>sum:</span> ${bill.billSum}</div></div></div></template>"; });
define('text!daily-bill/statistics.html', ['module'], function(module) { module.exports = "<template>Statistics:<div class=\"statistics-button-panel\"><div class=\"filter-field\"><div class=\"filter-title\">Start Period Date:</div><div><input type=\"text\" value.bind=\"startDateStr\"></div></div><div class=\"filter-field\"><div class=\"filter-title\">End Period Date:</div><div><input type=\"text\" value.bind=\"endDateStr\"></div></div><div class=\"filter-field\"><div class=\"filter-title\">Product Name:</div><div><input type=\"text\" value.bind=\"productName\"></div></div><button class=\"edit-bill-button\" click.delegate=\"updateStatistics()\">Update statistics</button></div><div class=\"total-sum\"><div class=\"title\">Total Sum:</div><div>${statisticsByProduct.totalSum}</div></div><div repeat.for=\"statistics of statisticsByProduct.statisticDetails\" class=\"statistics-item\"><div class=\"name\">${statistics.name}:</div><div class=\"value\">${statistics.price}</div></div><template></template></template>"; });
//# sourceMappingURL=app-bundle.js.map