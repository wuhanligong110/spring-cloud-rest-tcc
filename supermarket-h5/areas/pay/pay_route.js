angular.module('pay.route', ['pay.controller'])
  .config(function ($stateProvider, $urlRouterProvider) {

    $stateProvider
      .state('pay',{
          url: '/pay/:orderId/:amount',
          templateUrl: 'areas/pay/pay.html',
          controller: 'PayCtrl',
          params:{"orderId":null,"amount":null}
      })
  });
