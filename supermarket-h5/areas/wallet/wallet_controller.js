/******************************************************
 创建人：石国庆          创建时间：2015.10.20
 创建人：                修改时间：
 功  能：购物车
 ******************************************************/
angular.module('wallet.controller', ['wallet.service'])
  .controller('WalletCtrl', ['$scope', '$state','$stateParams','$ionicHistory','IndexedDBJs','CommonJs','WalletFty',function ($scope, $state,$stateParams,$ionicHistory,IndexedDBJs,CommonJs,WalletFty) {

      $scope.$on('$ionicView.beforeEnter', function (e) {
          initWalletInfo();
      });

      function initWalletInfo(){
          $scope.wallet = {withdrawalCredit:$stateParams.withdrawalCredit,shopCredit:$stateParams.shopCredit}
      }

    // 返回按钮方法
    $scope.func_goBack = function () {
      $ionicHistory.goBack();
    };

    $scope.func_goHome= function () {
      $state.go('tab.home');
    }
  }]);
