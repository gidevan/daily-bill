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
            config.map([{ route: '', moduleId: 'daily-bill/bill-list', title: 'Bill List', name: 'billList' }, { route: 'bill/:id', moduleId: 'daily-bill/bill-details', name: 'billDetails' }, { route: 'bill/add', moduleId: 'daily-bill/add-bill', name: 'addBill' }, { route: 'bill/edit/:id', moduleId: 'daily-bill/add-bill', name: 'editBill' }, { route: 'statistics', moduleId: 'daily-bill/statistics', name: 'statistics' }, { route: 'product/list', moduleId: 'daily-bill/product/product-list', name: 'product-list' }, { route: 'product/edit/:id', moduleId: 'daily-bill/product/product-item', name: 'product-item' }, { route: 'shop/list', moduleId: 'daily-bill/shop/shop-list', name: 'shop-list' }, { route: 'shop/edit/:id', moduleId: 'daily-bill/shop/shop-item', name: 'shop-item' }]);

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
                return self.shops;
            }).then(function (shops) {
                return self.dailyBillService.getProducts();
            }).then(function (response) {
                return response.json();
            }).then(function (prodData) {
                console.log('products: ');
                console.log(prodData);
                console.log(prodData.object);
                self.products = prodData.object;
                return self.products;
            }).then(function (products) {
                if (params.id) {
                    return self.dailyBillService.getBillById(params.id).then(function (response) {
                        return response.json();
                    }).then(function (data) {
                        console.log('bill: ');
                        console.log(data.object);
                        self.bill = data.object;
                        self.initOpenBill();
                        console.log('bill date: ');
                        console.log(self.bill.date);
                        return self.bill;
                    });
                } else {
                    _this.createBill();
                }
            }).catch(function (error) {
                console.log('Error getting data');
                console.log(error);
                self.shops = [];
                self.products = [];
            });
        };

        AddBill.prototype.attached = function attached() {
            console.log('attached add-bill');
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
            var date = new Date();
            this.startDateStr = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + 1;
            this.endDateStr = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
        }

        BillList.prototype.activate = function activate(params, routeConfig) {
            this.findBills();
        };

        BillList.prototype.viewBillDetails = function viewBillDetails(id) {
            console.log("viewBillDetails: " + id);
            this.router.navigateToRoute('billDetails', { id: id });
        };

        BillList.prototype.findBills = function findBills() {
            var startDate = Date.parse(this.startDateStr);
            var endDate = Date.parse(this.endDateStr);
            this.params = {
                startPeriodDate: startDate,
                endPeriodDate: endDate
            };
            var self = this;
            console.log('params get bill');
            console.log(this.params);
            this.dailyBillService.getBills(this.params).then(function (response) {
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

            this.productNames = [{ name: '' }];
            this.startDateStr = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + 1;
            this.endDateStr = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
        }

        Statistics.prototype.activate = function activate(params, routeConfig) {
            this.getStatisticsByProduct(this.params);
        };

        Statistics.prototype.getStatisticsByProduct = function getStatisticsByProduct(params) {
            var names = this.productNames.map(function (it) {
                return it.name;
            }).filter(function (it) {
                return it;
            });
            params.productNames = names;
            console.log(params);
            var self = this;
            this.dailyBillService.getStatisticByProduct(params).then(function (response) {
                return response.json();
            }).then(function (data) {
                console.log(data);
                self.statisticsByProduct = data.object;
                self.statisticsByProduct.statisticDetails.map(function (it) {
                    it.active = true;
                    return it;
                });
                self.statisticsByProduct.allEnabled = true;
                self.statisticsByProduct.totalSumCalculated = self.statisticsByProduct.totalSum;
            });
        };

        Statistics.prototype.updateStatistics = function updateStatistics() {
            var startDate = Date.parse(this.startDateStr);
            var endDate = Date.parse(this.endDateStr);
            this.params = {
                startPeriodDate: startDate,
                endPeriodDate: endDate
            };
            this.getStatisticsByProduct(this.params);
        };

        Statistics.prototype.switchItem = function switchItem(statisticsItem) {
            statisticsItem.active = !statisticsItem.active;
            this.statisticsByProduct.totalSumCalculated = !statisticsItem.active ? this.statisticsByProduct.totalSumCalculated - statisticsItem.price : this.statisticsByProduct.totalSumCalculated + statisticsItem.price;
        };

        Statistics.prototype.addProductName = function addProductName() {
            this.productNames.push({ name: '' });
        };

        Statistics.prototype.switchItems = function switchItems() {
            this.statisticsByProduct.allEnabled = !this.statisticsByProduct.allEnabled;
            this.statisticsByProduct.totalSumCalculated = !this.statisticsByProduct.allEnabled ? 0 : this.statisticsByProduct.totalSum;
            var self = this;
            this.statisticsByProduct.statisticDetails.map(function (it) {
                it.active = self.statisticsByProduct.allEnabled;
            });
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
define('daily-bill/product/product-item',["exports", "../service/daily-bill-service", "aurelia-router"], function (exports, _dailyBillService, _aureliaRouter) {
    "use strict";

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.ProductItem = undefined;

    function _classCallCheck(instance, Constructor) {
        if (!(instance instanceof Constructor)) {
            throw new TypeError("Cannot call a class as a function");
        }
    }

    var ProductItem = exports.ProductItem = function () {
        ProductItem.inject = function inject() {
            return [_dailyBillService.DailyBillService, _aureliaRouter.Router];
        };

        function ProductItem(dailyBillService, router) {
            _classCallCheck(this, ProductItem);

            this.dailyBillService = dailyBillService;
            this.router = router;
        }

        ProductItem.prototype.activate = function activate(params, routeConfig) {
            var id = params.id;
            this.initProduct(id);
        };

        ProductItem.prototype.initProduct = function initProduct(id) {
            this.errorMessages = [];
            var self = this;
            this.dailyBillService.getProductById(id).then(function (response) {
                return response.json();
            }).then(function (data) {
                self.product = data.object;
                console.log("product:", self.product);
            }).catch(function (e) {
                console.log("error get bill:" + e);
            });
        };

        ProductItem.prototype.updateProduct = function updateProduct() {
            var _this = this;

            this.errorMessages = [];
            var self = this;
            this.dailyBillService.updateProduct(this.product).then(function (response) {
                return response.json();
            }).then(function (data) {
                console.log("update product result");
                console.log(data);
                if (data.code == '200') {
                    _this.router.navigateToRoute('product-list');
                } else {
                    self.errorMessages.push(data.message);
                }
            });
        };

        return ProductItem;
    }();
});
define('daily-bill/product/product-list',["exports", "../service/daily-bill-service", "aurelia-router"], function (exports, _dailyBillService, _aureliaRouter) {
    "use strict";

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.ProductList = undefined;

    function _classCallCheck(instance, Constructor) {
        if (!(instance instanceof Constructor)) {
            throw new TypeError("Cannot call a class as a function");
        }
    }

    var ProductList = exports.ProductList = function () {
        ProductList.inject = function inject() {
            return [_dailyBillService.DailyBillService, _aureliaRouter.Router];
        };

        function ProductList(dailyBillService, router) {
            _classCallCheck(this, ProductList);

            this.dailyBillService = dailyBillService;
            this.router = router;
            this.initProducts();
            this.filterValue = "";
        }

        ProductList.prototype.initProducts = function initProducts() {
            var self = this;
            this.errorMessages = [];
            this.products = [];
            this.filteredProducts = [];
            this.dailyBillService.getAllProducts().then(function (response) {
                return response.json();
            }).then(function (data) {
                self.products = data.object;
                self.filteredProducts = data.object;
            }).catch(function (e) {
                self.errorMessages.push(e);
            });
        };

        ProductList.prototype.editProduct = function editProduct(id) {
            this.router.navigateToRoute('product-item', { id: id });
        };

        ProductList.prototype.filterChange = function filterChange() {
            var _this = this;

            if (this.filterValue) {
                this.filteredProducts = this.products.filter(function (it) {
                    return it.name.toLowerCase().indexOf(_this.filterValue.toLowerCase()) >= 0;
                });
            } else {
                this.filteredProducts = this.products;
            }
        };

        return ProductList;
    }();
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

        DailyBillService.prototype.getBills = function getBills(params) {
            console.log("Get bills");
            return httpClient.fetch('http://localhost:8080/daily-bill/bills', {
                method: "POST",
                body: (0, _aureliaFetchClient.json)(params)
            });
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

        DailyBillService.prototype.findLastPrice = function findLastPrice(shopId, productId) {
            return httpClient.fetch('http://localhost:8080/daily-bill/last/price/shop/' + shopId + '/product/' + productId);
        };

        DailyBillService.prototype.getAllShops = function getAllShops() {
            return httpClient.fetch("http://localhost:8080/shops/all");
        };

        DailyBillService.prototype.getShopById = function getShopById(id) {
            return httpClient.fetch("http://localhost:8080/shops/" + id);
        };

        DailyBillService.prototype.updateShop = function updateShop(shop) {
            return httpClient.fetch("http://localhost:8080/shops", {
                method: "POST",
                body: (0, _aureliaFetchClient.json)(shop)
            });
        };

        DailyBillService.prototype.getAllProducts = function getAllProducts() {
            return httpClient.fetch("http://localhost:8080/products/all");
        };

        DailyBillService.prototype.getProductById = function getProductById(id) {
            return httpClient.fetch("http://localhost:8080/products/" + id);
        };

        DailyBillService.prototype.updateProduct = function updateProduct(product) {
            return httpClient.fetch("http://localhost:8080/products", {
                method: "POST",
                body: (0, _aureliaFetchClient.json)(product)
            });
        };

        return DailyBillService;
    }();
});
define('daily-bill/shop/shop-item',["exports", "../service/daily-bill-service", "aurelia-router"], function (exports, _dailyBillService, _aureliaRouter) {
    "use strict";

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.ShopItem = undefined;

    function _classCallCheck(instance, Constructor) {
        if (!(instance instanceof Constructor)) {
            throw new TypeError("Cannot call a class as a function");
        }
    }

    var ShopItem = exports.ShopItem = function () {
        ShopItem.inject = function inject() {
            return [_dailyBillService.DailyBillService, _aureliaRouter.Router];
        };

        function ShopItem(dailyBillService, router) {
            _classCallCheck(this, ShopItem);

            this.dailyBillService = dailyBillService;
            this.router = router;
            this.errorMessages = [];
        }

        ShopItem.prototype.activate = function activate(params, routeConfig) {
            var id = params.id;
            this.initShop(id);
        };

        ShopItem.prototype.initShop = function initShop(id) {
            var self = this;
            this.dailyBillService.getShopById(id).then(function (response) {
                return response.json();
            }).then(function (data) {
                self.shop = data.object;
                console.log("shop:", self.shop);
            }).catch(function (e) {
                console.log("error get bill:" + e);
            });
        };

        ShopItem.prototype.updateShop = function updateShop() {
            var _this = this;

            this.errorMessages = [];
            var self = this;
            this.dailyBillService.updateShop(this.shop).then(function (response) {
                return response.json();
            }).then(function (data) {
                console.log("update shop result");
                console.log(data);
                if (data.code == '200') {
                    _this.router.navigateToRoute('shop-list');
                } else {
                    self.errorMessages.push(data.message);
                }
            });
        };

        return ShopItem;
    }();
});
define('daily-bill/shop/shop-list',["exports", "../service/daily-bill-service", "aurelia-router"], function (exports, _dailyBillService, _aureliaRouter) {
    "use strict";

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.ShopList = undefined;

    function _classCallCheck(instance, Constructor) {
        if (!(instance instanceof Constructor)) {
            throw new TypeError("Cannot call a class as a function");
        }
    }

    var ShopList = exports.ShopList = function () {
        ShopList.inject = function inject() {
            return [_dailyBillService.DailyBillService, _aureliaRouter.Router];
        };

        function ShopList(dailyBillService, router) {
            _classCallCheck(this, ShopList);

            this.dailyBillService = dailyBillService;
            this.router = router;
            this.initShops();
            this.filterValue = "";
        }

        ShopList.prototype.initShops = function initShops() {
            var self = this;
            this.errorMessages = [];
            this.dailyBillService.getAllShops().then(function (response) {
                return response.json();
            }).then(function (data) {
                self.shops = data.object;
                self.filteredShops = data.object;
            }).catch(function (e) {
                self.errorMessages.push(e);
            });
        };

        ShopList.prototype.editShop = function editShop(id) {
            this.router.navigateToRoute('shop-item', { id: id });
        };

        ShopList.prototype.filterChange = function filterChange() {
            var _this = this;

            console.log("filter shop changed");
            if (this.filterValue) {
                this.filteredShops = this.shops.filter(function (it) {
                    return it.name.toLowerCase().indexOf(_this.filterValue.toLowerCase()) >= 0;
                });
            } else {
                this.filteredShops = this.shops;
            }
        };

        return ShopList;
    }();
});
define('daily-bill/components/bill-item/edit-bill-item',["exports", "aurelia-framework", "../../service/daily-bill-service"], function (exports, _aureliaFramework, _dailyBillService) {
    "use strict";

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.EditBillItem = undefined;

    var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) {
        return typeof obj;
    } : function (obj) {
        return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj;
    };

    function _initDefineProp(target, property, descriptor, context) {
        if (!descriptor) return;
        Object.defineProperty(target, property, {
            enumerable: descriptor.enumerable,
            configurable: descriptor.configurable,
            writable: descriptor.writable,
            value: descriptor.initializer ? descriptor.initializer.call(context) : void 0
        });
    }

    function _classCallCheck(instance, Constructor) {
        if (!(instance instanceof Constructor)) {
            throw new TypeError("Cannot call a class as a function");
        }
    }

    function _applyDecoratedDescriptor(target, property, decorators, descriptor, context) {
        var desc = {};
        Object['ke' + 'ys'](descriptor).forEach(function (key) {
            desc[key] = descriptor[key];
        });
        desc.enumerable = !!desc.enumerable;
        desc.configurable = !!desc.configurable;

        if ('value' in desc || desc.initializer) {
            desc.writable = true;
        }

        desc = decorators.slice().reverse().reduce(function (desc, decorator) {
            return decorator(target, property, desc) || desc;
        }, desc);

        if (context && desc.initializer !== void 0) {
            desc.value = desc.initializer ? desc.initializer.call(context) : void 0;
            desc.initializer = undefined;
        }

        if (desc.initializer === void 0) {
            Object['define' + 'Property'](target, property, desc);
            desc = null;
        }

        return desc;
    }

    function _initializerWarningHelper(descriptor, context) {
        throw new Error('Decorating class property failed. Please ensure that transform-class-properties is enabled.');
    }

    var _desc, _value, _class, _descriptor, _descriptor2, _descriptor3;

    var EditBillItem = exports.EditBillItem = (_class = function () {
        EditBillItem.inject = function inject() {
            return [_dailyBillService.DailyBillService];
        };

        function EditBillItem(dailyBillService) {
            var _this = this;

            _classCallCheck(this, EditBillItem);

            _initDefineProp(this, "billItem", _descriptor, this);

            _initDefineProp(this, "products", _descriptor2, this);

            _initDefineProp(this, "shopId", _descriptor3, this);

            this.findLastPrice = function (productId) {
                console.log('bill-item: find last price: ' + productId);
                console.log(_this.shopId);
                if (_this.shopId) {
                    var _ret = function () {
                        var self = _this;
                        return {
                            v: _this.dailyBillService.findLastPrice(_this.shopId, productId).then(function (response) {
                                return response.json();
                            }).then(function (data) {
                                console.log('last price');
                                console.log(data);
                                var lastPrice = data.object;
                                self.billItem.price = lastPrice ? lastPrice : 0;
                                return data;
                            })
                        };
                    }();

                    if ((typeof _ret === "undefined" ? "undefined" : _typeof(_ret)) === "object") return _ret.v;
                }
            };

            console.log("add bill item constructor");
            this.dailyBillService = dailyBillService;
        }

        return EditBillItem;
    }(), (_descriptor = _applyDecoratedDescriptor(_class.prototype, "billItem", [_aureliaFramework.bindable], {
        enumerable: true,
        initializer: null
    }), _descriptor2 = _applyDecoratedDescriptor(_class.prototype, "products", [_aureliaFramework.bindable], {
        enumerable: true,
        initializer: null
    }), _descriptor3 = _applyDecoratedDescriptor(_class.prototype, "shopId", [_aureliaFramework.bindable], {
        enumerable: true,
        initializer: null
    })), _class);
});
define('daily-bill/components/filtered-select/filtered-select',['exports', 'aurelia-framework'], function (exports, _aureliaFramework) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.FilteredSelect = undefined;

    function _initDefineProp(target, property, descriptor, context) {
        if (!descriptor) return;
        Object.defineProperty(target, property, {
            enumerable: descriptor.enumerable,
            configurable: descriptor.configurable,
            writable: descriptor.writable,
            value: descriptor.initializer ? descriptor.initializer.call(context) : void 0
        });
    }

    function _classCallCheck(instance, Constructor) {
        if (!(instance instanceof Constructor)) {
            throw new TypeError("Cannot call a class as a function");
        }
    }

    function _applyDecoratedDescriptor(target, property, decorators, descriptor, context) {
        var desc = {};
        Object['ke' + 'ys'](descriptor).forEach(function (key) {
            desc[key] = descriptor[key];
        });
        desc.enumerable = !!desc.enumerable;
        desc.configurable = !!desc.configurable;

        if ('value' in desc || desc.initializer) {
            desc.writable = true;
        }

        desc = decorators.slice().reverse().reduce(function (desc, decorator) {
            return decorator(target, property, desc) || desc;
        }, desc);

        if (context && desc.initializer !== void 0) {
            desc.value = desc.initializer ? desc.initializer.call(context) : void 0;
            desc.initializer = undefined;
        }

        if (desc.initializer === void 0) {
            Object['define' + 'Property'](target, property, desc);
            desc = null;
        }

        return desc;
    }

    function _initializerWarningHelper(descriptor, context) {
        throw new Error('Decorating class property failed. Please ensure that transform-class-properties is enabled.');
    }

    var _desc, _value, _class, _descriptor, _descriptor2, _descriptor3;

    var FilteredSelect = exports.FilteredSelect = (_class = function () {
        function FilteredSelect() {
            _classCallCheck(this, FilteredSelect);

            _initDefineProp(this, 'selectedItem', _descriptor, this);

            _initDefineProp(this, 'items', _descriptor2, this);

            _initDefineProp(this, 'callbackFn', _descriptor3, this);
        }

        FilteredSelect.prototype.attached = function attached() {
            if (this.items) {
                this.filteredItems = this.items;
            }
            if (this.selectedItem) {
                this.filterValue = this.selectedItem.name;
            }
        };

        FilteredSelect.prototype.searchByFilterValue = function searchByFilterValue(it, filterValues) {
            var condition = true;
            for (var _iterator = filterValues, _isArray = Array.isArray(_iterator), _i = 0, _iterator = _isArray ? _iterator : _iterator[Symbol.iterator]();;) {
                var _ref;

                if (_isArray) {
                    if (_i >= _iterator.length) break;
                    _ref = _iterator[_i++];
                } else {
                    _i = _iterator.next();
                    if (_i.done) break;
                    _ref = _i.value;
                }

                var s = _ref;

                condition = condition && it.name.toLowerCase().indexOf(s.toLowerCase()) >= 0;
            }
            return condition;
        };

        FilteredSelect.prototype.filterChange = function filterChange() {
            var _this = this;

            if (this.filterValue) {
                (function () {
                    var delimeter = /\s+/;
                    var filterValues = _this.filterValue.split(delimeter).filter(function (i) {
                        return i !== '';
                    });
                    _this.filteredItems = _this.items.filter(function (it) {
                        return _this.searchByFilterValue(it, filterValues);
                    });
                })();
            } else {
                this.filteredItems = this.items;
            }
            var value = this.items.find(function (element, index, array) {
                return element.name.toLowerCase() === _this.filterValue.toLowerCase();
            });
            if (value) {
                this.selectedItem = value;
                this.filterSelectedItem = value;
            } else {
                this.selectedItem = {
                    id: null,
                    name: this.filterValue
                };
            }
        };

        FilteredSelect.prototype.selectItem = function selectItem() {
            console.log('select item');
            this.filterValue = this.filterSelectedItem.name;
            this.selectedItem = this.filterSelectedItem;
            if (this.callbackFn) {
                this.callbackFn(this.selectedItem.id);
            }
        };

        return FilteredSelect;
    }(), (_descriptor = _applyDecoratedDescriptor(_class.prototype, 'selectedItem', [_aureliaFramework.bindable], {
        enumerable: true,
        initializer: null
    }), _descriptor2 = _applyDecoratedDescriptor(_class.prototype, 'items', [_aureliaFramework.bindable], {
        enumerable: true,
        initializer: null
    }), _descriptor3 = _applyDecoratedDescriptor(_class.prototype, 'callbackFn', [_aureliaFramework.bindable], {
        enumerable: true,
        initializer: null
    })), _class);
});
define('text!app.html', ['module'], function(module) { module.exports = "<template><require from=\"./css/daily-bill.css\"></require><nav role=\"navigation\"><div><a href=\"#\"><span>Bills</span> </a><span>|</span> <a route-href=\"route: addBill\"><span>Add Bill</span> </a><span>|</span> <a route-href=\"route: statistics\"><span>Statistics</span> </a><span>|</span> <a route-href=\"route: product-list\"><span>Product list</span> </a><span>|</span> <a route-href=\"route: shop-list\"><span>Shop list</span></a></div></nav><div class=\"container\"><router-view></router-view></div></template>"; });
define('text!css/daily-bill.css', ['module'], function(module) { module.exports = "\n.bill-list .bill {\n  border: 1px solid black;\n  margin: 1px;\n}\n\n.bill-list .bill .bill-info>span {\n  font-weight: bolder;\n}\n\n.shop-info {\n  display: flex;\n  flex-direction: row;\n}\n.item{\n  border: 1px solid black;\n  margin: 2px;\n}\n\n.bill-item-info {\n  display: flex;\n  flex-direction: row;\n}\n\n.shop-info > p, .bill-item-info >p{\n  font-weight: bolder;\n  width: 20%;\n}\n.shop-info > input, .shop-info > filtered-select {\n    width: 75%\n}\n.bill-item {\n  display: flex;\n  flex-direction: row;\n  border: 1px solid black;\n  margin: 1px;\n  padding: 1px;\n}\n.bill-item .item-description {\n  font-weight: bolder;\n  margin-right: 2px;\n  margin-left: 2px;\n  width: 20%;\n}\n.bill-item-info > input {\n    width: 75%;\n}\n.bill-item-info > filtered-select {\n    width: 75%;\n}\n.item-button-panel button {\n    width: 100%;\n    font-size: 24px;\n    margin: 2px;\n}\n.bill-button-panel button {\n  width: 100%;\n  font-size: 24px;\n  margin: 2px;\n}\n\n.bill-details {\n    border: 1px solid black;\n}\n\n.bill-details .bill-info {\n    margin: 2px;\n    padding-bottom: 3px;\n    font-size: 20px;\n\n}\n\n.bill-details .bill-info .bill-info-item {\n    display: flex;\n    flex-direction: row;\n}\n\n.bill-details .bill-info .bill-info-item .bill-info-description {\n    font-weight: bolder;\n    width: 20%;\n}\n.bill-items .bill-item {\n    display: flex;\n    flex-direction: column;\n}\n\n.bill-items .bill-item .bill-item-info-data  {\n    display: flex;\n    flex-direction: row;\n}\n\n.statistics-item {\n    border: 1px solid black;\n    margin: 2px;\n    padding: 2px;\n    display: flex;\n    flex-direction: row;\n}\n\n.statistics-item .name {\n    font-weight: bolder;\n    margin-right: 5px;\n}\n\n.statistics-item .switch-item-button {\n    border: 1px solid black;\n    margin-left:auto;\n    margin-right:0;\n    width: 40px;\n    height: 20px;\n}\n\n.statistics-item .active {\n    background-color: rgb(204, 204, 204);\n}\n.statistics-button-panel .name-filter {\n    display: flex;\n    flex-direction: row;\n}\n\n.statistics-button-panel .date-filter, .statistics-button-panel .date-filter > div {\n    display: flex;\n    flex-direction: row;\n}\n\n.statistics-button-panel .filter-field,  .bill-list-button-panel .filter-field {\n    margin: 2px;\n}\n\n.statistics-button-panel .filter-field .filter-title, .bill-list-button-panel .filter-field .filter-title {\n    font-weight: bolder;\n}\n\n.statistics-button-panel .add-product-button {\n    width: 100%;\n    font-weight: bolder;\n    padding: 2px;\n    margin: 2px;\n}\n\n.statistics-button-panel .edit-bill-button {\n    width: 100%;\n    font-weight: bolder;\n    padding: 2px;\n    margin: 2px;\n    height: 40px;\n}\n\n.total-sum {\n    display: flex;\n    flex-direction: row;\n    margin: 3px;\n}\n\n.total-sum .title {\n    font-weight: bolder;\n    margin-right: 5px;\n}\n\n.select-button .enable-button {\n    border: 1px solid black;\n}\n\n.filtered-select {\n    display: flex;\n    flex-direction: column;\n}\n\n.bill-list-button-panel {\n    display: flex;\n    flex-direction: row;\n}\n.bill-list-button-panel .filter-field {\n    display: flex;\n    flex-direction: row;\n}\n.bill-list-button-panel .edit-bill-button {\n    font-weight: bolder;\n    padding: 2px;\n    margin: 2px;\n    width: 20%;\n}\n\n.shop-list .shop-item, .product-list .product-item {\n    border: 1px solid black;\n    margin-top: 1px;\n}\n\n.shop-list .shop-info > span, .product-list .product-info > span {\n    font-weight: bolder;\n}\n\n.shop-details .shop-info > span, .product-details .product-info > span {\n    font-weight: bolder;\n}\n\n.error-messages p {\n    color : red;\n}\n\n.product-filter .filter-value > input, .shop-filter .filter-value > input{\n    width: 100%;\n}\n\n\n\n"; });
define('text!daily-bill/add-bill.html', ['module'], function(module) { module.exports = "<template><require from=\"./components/filtered-select/filtered-select\"></require><require from=\"./components/bill-item/edit-bill-item\"></require><div if.bind=\"bill\"><h1>Add Bill</h1><div class=\"shop-info\"><p>Shop name:</p><filtered-select items.two-way=\"shops\" selected-item.two-way=\"bill.shop\"></filtered-select></div><div class=\"shop-info\"><p>Date:</p><input type=\"text\" value.bind=\"bill.dateStr\"></div><div class=\"bill-items\"><div class=\"item\" repeat.for=\"billItem of bill.items\"><edit-bill-item bill-item.two-way=\"billItem\" products.two-way=\"products\" shop-id.two-way=\"bill.shop.id\"></edit-bill-item></div><div class=\"item-button-panel\"><button type=\"button\" click.delegate=\"addBillItem()\">Add item</button></div></div><div class=\"bill-button-panel\" if.bind=\"!bill.id\"><button type=\"button\" click.delegate=\"addBill()\">Add bill</button></div><div class=\"bill-button-panel\" if.bind=\"bill.id\"><button type=\"button\" click.delegate=\"updateBill()\">Update bill</button></div></div></template>"; });
define('text!daily-bill/bill-details.html', ['module'], function(module) { module.exports = "<template><div class=\"bill-details\"><div class=\"bill-info\"><div class=\"bill-info-item\"><div class=\"bill-info-description\">bill id:</div><div class=\"bill-info-item\">${bill.id}</div></div><div class=\"bill-info-item\"><div class=\"bill-info-description\">date:</div><div class=\"bill-info-item\">${bill.dateStr}</div></div><div class=\"bill-info-item\"><div class=\"bill-info-description\">shop id:</div><div class=\"bill-info-item\">${bill.shop.id}</div></div><div class=\"bill-info-item\"><div class=\"bill-info-description\">shop:</div><div class=\"bill-info-item\">${bill.shop.name}</div></div><div class=\"bill-info-item\"><div class=\"bill-info-description\">bill sum:</div><div class=\"bill-info-item\">${bill.billSum}</div></div></div><div class=\"bill-items\"><div class=\"bill-item\" repeat.for=\"item of bill.items\"><div class=\"bill-item-info-data\"><div class=\"item-description\">product id:</div><div class=\"item-value\">${item.product.id}</div></div><div class=\"bill-item-info-data\"><div class=\"item-description\">product:</div><div class=\"item-value\">${item.product.name}</div></div><div class=\"bill-item-info-data\"><div class=\"item-description\">price:</div><div class=\"item-value\">${item.price}</div></div><div class=\"bill-item-info-data\"><div class=\"item-description\">amount:</div><div class=\"item-value\">${item.amount}</div></div><div class=\"bill-item-info-data\"><div class=\"item-description\">sum:</div><div class=\"item-value\">${item.amount * item.price}</div></div></div></div></div><div class=\"bill-button-panel\"><button class=\"edit-bill-button\" click.delegate=\"edit()\">Edit bill</button></div></template>"; });
define('text!daily-bill/bill-list.html', ['module'], function(module) { module.exports = "<template><h1>Bill List</h1><div class=\"bill-list-button-panel\"><div class=\"filter-field\"><div class=\"filter-title\">Start Period Date:</div><div><input type=\"text\" value.bind=\"startDateStr\"></div></div><div class=\"filter-field\"><div class=\"filter-title\">End Period Date:</div><div><input type=\"text\" value.bind=\"endDateStr\"></div></div><button class=\"edit-bill-button\" click.delegate=\"findBills()\">Find</button></div><div class=\"bill-list\"><div repeat.for=\"bill of bills\" class=\"bill\" click.delegate=\"viewBillDetails(bill.id)\"><div class=\"bill-info\"><span>id:</span> ${bill.id}</div><div class=\"bill-info\"><span>shop:</span> ${bill.shopName}</div><div class=\"bill-info\"><span>date:</span> ${bill.dateStr}</div><div class=\"bill-info\"><span>sum:</span> ${bill.billSum}</div></div></div></template>"; });
define('text!daily-bill/statistics.html', ['module'], function(module) { module.exports = "<template>Statistics:<div class=\"statistics-button-panel\"><div class=\"date-filter\"><div class=\"filter-field\"><div class=\"filter-title\">Start Period Date:</div><div><input type=\"text\" value.bind=\"startDateStr\"></div></div><div class=\"filter-field\"><div class=\"filter-title\">End Period Date:</div><div><input type=\"text\" value.bind=\"endDateStr\"></div></div></div><div class=\"name-filter\"><div class=\"filter-field\" repeat.for=\"productName of productNames\"><div class=\"filter-title\">Product Name:</div><div><input type=\"text\" value.bind=\"productName.name\"></div></div><button class=\"add-product-button\" click.delegate=\"addProductName()\">Add product name</button></div><button class=\"edit-bill-button\" click.delegate=\"updateStatistics()\">Update statistics</button></div><div class=\"total-sum\"><div class=\"title\">Total Sum:</div><div>${statisticsByProduct.totalSum}</div></div><div class=\"total-sum\"><div class=\"title\">Total Sum Selected Items:</div><div>${statisticsByProduct.totalSumCalculated}</div></div><div class=\"total-sum\"><div class=\"title\">Total Sum Unselected Items:</div><div>${statisticsByProduct.totalSum - statisticsByProduct.totalSumCalculated}</div></div><div class=\"select-button\"><div class=\"enable-button\" click.delegate=\"switchItems()\">all enabled: ${statisticsByProduct.allEnabled}</div></div><div repeat.for=\"statistics of statisticsByProduct.statisticDetails\" class=\"statistics-item\"><div class=\"name\">${statistics.name}:</div><div class=\"value\">${statistics.price}</div><div class=\"switch-item-button ${statistics.active ? 'active' : ''}\" click.delegate=\"switchItem(statistics)\"></div></div><template></template></template>"; });
define('text!daily-bill/product/product-item.html', ['module'], function(module) { module.exports = "<template><h2>Product Item</h2><div class=\"error-messages\"><p repeat.for=\"msg of errorMessages\">${msg}</p></div><div class=\"product-details\"><div class=\"product-info\"><span>id:</span> ${product.id}</div><div class=\"product-info\"><span>name:</span> <input type=\"text\" value.bind=\"product.name\"></div><div class=\"product-info\"><span>active:</span> <input type=\"checkbox\" checked.bind=\"product.active\"></div></div><div class=\"bill-button-panel\"><button type=\"button\" click.delegate=\"updateProduct()\">Update product</button></div></template>"; });
define('text!daily-bill/product/product-list.html', ['module'], function(module) { module.exports = "<template><h2>Product list</h2><div class=\"error-messages\"><p repeat.for=\"msg of errorMessages\">${msg}</p></div><div class=\"product-filter\"><div class=\"filter-value\"><input type=\"text\" value.bind=\"filterValue\" change.delegate=\"filterChange()\"></div></div><div class=\"product-list\"><div repeat.for=\"product of filteredProducts\" class=\"product-item\" click.delegate=\"editProduct(product.id)\"><div class=\"product-info\"><span>id:</span> ${product.id}</div><div class=\"product-info\"><span>name:</span> ${product.name}</div><div class=\"product-info\"><span>active:</span> ${product.active}</div></div></div></template>"; });
define('text!daily-bill/shop/shop-item.html', ['module'], function(module) { module.exports = "<template><h2>Shop Item</h2><div class=\"error-messages\"><p repeat.for=\"msg of errorMessages\">${msg}</p></div><div class=\"shop-details\"><div class=\"shop-info\"><span>id:</span> ${shop.id}</div><div class=\"shop-info\"><span>name:</span> <input type=\"text\" value.bind=\"shop.name\"></div><div class=\"shop-info\"><span>active:</span> <input type=\"checkbox\" checked.bind=\"shop.active\"></div></div><div class=\"bill-button-panel\"><button type=\"button\" click.delegate=\"updateShop()\">Update shop</button></div></template>"; });
define('text!daily-bill/shop/shop-list.html', ['module'], function(module) { module.exports = "<template><h2>Shop list:</h2><div class=\"error-messages\"><p repeat.for=\"msg of errorMessages\">${msg}</p></div><div class=\"shop-filter\"><div class=\"filter-value\"><input type=\"text\" value.bind=\"filterValue\" change.delegate=\"filterChange()\"></div></div><div class=\"shop-list\"><div repeat.for=\"shop of filteredShops\" class=\"shop-item\" click.delegate=\"editShop(shop.id)\"><div class=\"shop-info\"><span>id:</span> ${shop.id}</div><div class=\"shop-info\"><span>name:</span> ${shop.name}</div><div class=\"shop-info\"><span>active:</span> ${shop.active}</div></div></div></template>"; });
define('text!daily-bill/components/bill-item/edit-bill-item.html', ['module'], function(module) { module.exports = "<template><require from=\"../filtered-select/filtered-select\"></require><div class=\"bill-item-info\"><p>Product:</p><filtered-select items.two-way=\"products\" selected-item.two-way=\"billItem.product\" callback-fn.two-way=\"findLastPrice\"></filtered-select></div><div class=\"bill-item-info\"><p>Price:</p><input type=\"text\" value.bind=\"billItem.price\"></div><div class=\"bill-item-info\"><p>Amount:</p><input type=\"text\" value.bind=\"billItem.amount\"></div></template>"; });
define('text!daily-bill/components/filtered-select/filtered-select.html', ['module'], function(module) { module.exports = "<template><div class=\"filtered-select\"><input type=\"text\" value.bind=\"filterValue\" change.delegate=\"filterChange()\"><select value.bind=\"filterSelectedItem\" change.delegate=\"selectItem()\" size=\"6\"><option repeat.for=\"it of filteredItems\" model.bind=\"it\">${it.name}</option></select></div></template>"; });
//# sourceMappingURL=app-bundle.js.map