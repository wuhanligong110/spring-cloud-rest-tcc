/******************************************************
 创建人：石国庆          创建时间：2015.10.20
 创建人：                修改时间：
 功  能：购物车
 ******************************************************/
angular.module('myFavorite.controller', ['myFavorite.service'])
  .controller('MyFavoriteCtrl', ['$scope', '$state','$stateParams','$ionicHistory','IndexedDBJs','CommonJs','MyFavoriteFty',function ($scope, $state,$stateParams,$ionicHistory,IndexedDBJs,CommonJs,MyFavoriteFty) {

      $scope.$on('$ionicView.beforeEnter', function (e) {

      });

      function initShopCreditInfo(){

      }

    // 返回按钮方法
    $scope.func_goBack = function () {
      $ionicHistory.goBack();
    };

    $scope.func_goHome= function () {
      $state.go('tab.home');
    }
  }]);
