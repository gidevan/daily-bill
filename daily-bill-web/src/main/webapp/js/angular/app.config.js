'use strict';

angular
    .module('daily-bill')
    .config(['$locationProvider' ,'$routeProvider',
        function config($locationProvider, $routeProvider) {
            $locationProvider.hashPrefix('!');

            $routeProvider
                .when('/bill-list', {
                    template: '<bill-list></bill-list>'
                })
                .otherwise('/bill-list');
    }
  ]);