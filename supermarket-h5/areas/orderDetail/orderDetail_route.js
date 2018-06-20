angular.module('orderDetail.route', ['orderDetail.controller'])
  .config(function ($stateProvider, $urlRouterProvider) {

    $stateProvider
    .state('orderDetail', {
        url: '/order/detail/:orderId',
        templateUrl: 'areas/orderDetail/orderDetails.html',
        controller: 'OrderDetailCtrl',
        params:{"orderId":null}
    })

  });
