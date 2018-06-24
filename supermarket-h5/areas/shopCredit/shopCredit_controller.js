/******************************************************
 创建人：石国庆          创建时间：2015.10.20
 创建人：                修改时间：
 功  能：购物车
 ******************************************************/
angular.module('shopCredit.controller', ['shopCredit.service'])
  .controller('ShopCreditCtrl', ['$scope', '$state','$stateParams','$ionicHistory','IndexedDBJs','CommonJs','ShopCreditFty',function ($scope, $state,$stateParams,$ionicHistory,IndexedDBJs,CommonJs,ShopCreditFty) {

      $scope.$on('$ionicView.beforeEnter', function (e) {
          initShopCreditInfo();
      });

      function initShopCreditInfo(){
          $scope.shopCredit = $stateParams.shopCredit;
      }

    // 返回按钮方法
    $scope.func_goBack = function () {
      $ionicHistory.goBack();
    };

    $scope.func_goHome= function () {
      $state.go('tab.home');
    }
  }]);
