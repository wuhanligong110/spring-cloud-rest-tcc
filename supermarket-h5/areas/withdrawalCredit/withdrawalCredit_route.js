angular.module('withdrawalCredit.route', ['withdrawalCredit.controller'])
  .config(function ($stateProvider, $urlRouterProvider) {

    $stateProvider
      .state('withdrawalCredit', {
        url: '/withdrawalCredit/:withdrawalCredit',
        templateUrl: 'areas/withdrawalCredit/withdrawalCredit.html',
        controller: 'WithdrawalCreditCtrl',
        params:{"withdrawalCredit":null}
      })
  });
