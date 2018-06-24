// 总路由模块
angular.module('route', [
  'guidePage.route',
  'tab.route',
  'home.route',
  'category.route',
  'goodsList.route',
  'details.route',
  'cart.route',
  'account.route',
  'order.route',
  'address.list.route',
  'address.detail.route',
  'orderList.route',
  'orderDetail.route',
  'pay.route',
  'cards.route',
  'bankcard.route',
  'wallet.route',
  'withdrawalCredit.route',
  'creditList.route',
  'shopCredit.route',
  'myFavorite.route',
  'withdraw.route'
])
  .config(function($stateProvider, $urlRouterProvider) {

    // 第一次登陆
    /*if(localStorage["isFirst"])
    {
      $urlRouterProvider.otherwise('/tab/home');
    }
    else {
      $urlRouterProvider.otherwise('/guidePage');
    }*/
	$urlRouterProvider.otherwise('/tab/home');
  });
