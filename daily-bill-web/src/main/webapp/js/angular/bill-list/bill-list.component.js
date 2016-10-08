'use strict';

angular.module('daily-bill')
    .component('billList', {
        templateUrl: 'js/angular/bill-list/bill-list.html',
        controller: ['$routeParams', function($routeParams) {
            console.log('init bill list');
        }]
    })