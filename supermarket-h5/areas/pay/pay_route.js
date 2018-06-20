angular.module('pay.route', ['pay.controller'])
  .config(function ($stateProvider, $urlRouterProvider) {

    $stateProvider
      .state('pay',{
          url: '/pay/:orderId',
          templateUrl: 'areas/pay/pay.html',
          controller: 'PayCtrl'
      })
  });
