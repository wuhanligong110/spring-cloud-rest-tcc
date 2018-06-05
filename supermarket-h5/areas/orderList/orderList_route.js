angular.module('goodsList.route', ['goodsList.controller'])
  .config(function ($stateProvider, $urlRouterProvider) {

    $stateProvider
      .state('goodsList', {
        url: '/goodsList/:categotyId',
        templateUrl: 'areas/goodsList/orderList.html',
        controller: 'GoodsListCtrl',
        params:{"categotyId":null}
      })

  });
