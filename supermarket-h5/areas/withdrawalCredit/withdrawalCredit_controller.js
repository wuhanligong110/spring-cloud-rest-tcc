/******************************************************
 创建人：石国庆          创建时间：2015.10.20
 创建人：                修改时间：
 功  能：购物车
 ******************************************************/
angular.module('withdrawalCredit.controller', ['withdrawalCredit.service'])
  .controller('WithdrawalCreditCtrl', ['$scope', '$state','$stateParams','$ionicHistory','IndexedDBJs','CommonJs','WithdrawalCreditFty',function ($scope, $state,$stateParams,$ionicHistory,IndexedDBJs,CommonJs,WithdrawalCreditFty) {

      $scope.$on('$ionicView.beforeEnter', function (e) {
          initWithdrawalCreditInfo();
      });

      function initWithdrawalCreditInfo(){
          $scope.withdrawalCredit = $stateParams.withdrawalCredit;
      }

    // 返回按钮方法
    $scope.func_goBack = function () {
      $ionicHistory.goBack();
    };

    $scope.func_goHome= function () {
      $state.go('tab.home');
    }
  }]);
