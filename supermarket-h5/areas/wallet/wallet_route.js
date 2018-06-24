angular.module('wallet.route', ['wallet.controller'])
  .config(function ($stateProvider, $urlRouterProvider) {

    $stateProvider
      .state('wallet', {
        url: '/wallet/:withdrawalCredit/:shopCredit',
        templateUrl: 'areas/wallet/wallet.html',
        controller: 'WalletCtrl'
      })
  });
