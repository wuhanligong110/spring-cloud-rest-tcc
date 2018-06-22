angular.module('bankcard.route', ['bankcard.controller'])
  .config(function ($stateProvider, $urlRouterProvider) {

    $stateProvider
      .state('bankcard', {
        url: '/bankcard',
        templateUrl: 'areas/bankcard/bankcard.html',
        controller: 'BankcardCtrl'
      })
  });
