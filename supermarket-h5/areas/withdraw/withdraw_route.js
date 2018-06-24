angular.module('withdraw.route', ['withdraw.controller'])
  .config(function ($stateProvider, $urlRouterProvider) {

    $stateProvider
      .state('withdraw', {
        url: '/withdraw/:withdrawCredit',
        templateUrl: 'areas/withdraw/withdraw.html',
        controller: 'WithdrawCtrl',
        params:{"withdrawCredit":null}
      })
  });
