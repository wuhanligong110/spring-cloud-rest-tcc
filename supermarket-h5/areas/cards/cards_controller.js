/******************************************************
 创建人：石国庆          创建时间：2015.10.20
 创建人：                修改时间：
 功  能：购物车
 ******************************************************/
angular.module('cards.controller', ['cards.service'])
  .controller('CardsCtrl', ['$scope', '$state','$ionicHistory','IndexedDBJs','CommonJs','CardsFty','CardsFty', function ($scope, $state,$ionicHistory,IndexedDBJs,CommonJs,CardsFty,CardsFty) {

      $scope.$on('$ionicView.beforeEnter', function (e) {

      });


    // 返回按钮方法
    $scope.func_goBack = function () {
      $ionicHistory.goBack();
    };

    $scope.func_goHome= function () {
      $state.go('tab.home');
    }
  }]);
