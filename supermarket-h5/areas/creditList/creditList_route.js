angular.module('creditList.route', ['creditList.controller'])
  .config(function ($stateProvider, $urlRouterProvider) {

    $stateProvider
      .state('creditList', {
        url: '/creditList/:creditCategory/:creditType',
        templateUrl: 'areas/creditList/creditList.html',
        controller: 'CreditListCtrl',
        params:{"creditCategory":null,"creditType":null}
      })
  });
