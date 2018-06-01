/******************************************************
 创建人：石国庆          创建时间：2015.10.20
 创建人：                修改时间：
 功  能：购物车
 ******************************************************/
angular.module('order.controller', ['order.service'])
  .controller('OrderCtrl', ['$scope', '$state','$ionicHistory','IndexedDBJs','CommonJs','OrderFty', function ($scope, $state,$ionicHistory,IndexedDBJs,CommonJs,OrderFty) {

      $scope.$on('$ionicView.beforeEnter', function (e) {
          getDefaultConsignee();
          getSelectedProduct();
      });

    // 默认收件人
    function getDefaultConsignee(){
      var promise = OrderFty.getDefaultConsignee();
      promise.then(
        function (data) {
          $scope.consignee = data;
        },
        function (e) {
          CommonJs.AlertPopup(e);
        }
      ).finally(function () {
        });
    }

  function getSelectedProduct(){
      var promise = OrderFty.getSelectedProduct();
      promise.then(
          function (data) {
              $scope.order = data;
          },
          function (e) {
              CommonJs.AlertPopup(e);
          }
      ).finally(function () {
      });
  }

    // 返回按钮方法
    $scope.func_goBack = function () {
      $ionicHistory.goBack();
    };

    $scope.func_goHome= function () {
      $state.go('tab.home');
    }
  }]);
