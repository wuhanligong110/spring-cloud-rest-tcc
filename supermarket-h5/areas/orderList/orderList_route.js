angular.module('orderList.route', ['orderList.controller'])
  .config(function ($stateProvider, $urlRouterProvider) {

    $stateProvider
      .state('orderList', {
        url: '/orderList/:orderStatus',
        templateUrl: 'areas/orderList/orderList.html',
        controller: 'OrderListCtrl',
        params:{"orderStatus":null}
      })
    .state('order.detail', {
        url: '/order/detail/:orderId',
        templateUrl: 'areas/orderList/orderDetails.html',
        controller: 'OrderListCtrl',
        params:{"orderId":null}
    })

  });
