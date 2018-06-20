angular.module('orderList.route', ['orderList.controller'])
  .config(function ($stateProvider, $urlRouterProvider) {

    $stateProvider
      .state('orderList', {
        url: '/orderList/:orderStatus',
        templateUrl: 'areas/orderList/orderList.html',
        controller: 'OrderListCtrl',
        params:{"orderStatus":null}
      })
  });
