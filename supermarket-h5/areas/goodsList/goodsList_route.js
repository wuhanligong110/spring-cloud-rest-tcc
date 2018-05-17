angular.module('goodsList.route', ['goodsList.controller'])
  .config(function ($stateProvider, $urlRouterProvider) {

    $stateProvider
      .state('goodsList', {
        url: '/goodsList/:typeNumber/:type',
        templateUrl: 'areas/goodsList/goodsList.html',
        controller: 'GoodsListCtrl',
        params:{"type":null,"typeNumber":null}
      })

  });
