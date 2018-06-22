angular.module('bankcard.route', ['bankcard.controller'])
  .config(function ($stateProvider, $urlRouterProvider) {

    $stateProvider
      .state('bankcard', {
        url: '/bankcard/:accountType',
        templateUrl: 'areas/bankcard/bankcard.html',
        controller: 'BankcardCtrl',
        params:{"accountType":null}
      })
  });
