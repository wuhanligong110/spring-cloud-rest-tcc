angular.module('shopCredit.route', ['shopCredit.controller'])
  .config(function ($stateProvider, $urlRouterProvider) {

    $stateProvider
      .state('shopCredit', {
        url: '/shopCredit/:shopCredit',
        templateUrl: 'areas/shopCredit/shopCredit.html',
        controller: 'ShopCreditCtrl',
        params:{"shopCredit":null}
      })
  });
