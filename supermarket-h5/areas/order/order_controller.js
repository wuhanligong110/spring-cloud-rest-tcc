/******************************************************
 创建人：石国庆          创建时间：2015.10.20
 创建人：                修改时间：
 功  能：购物车
 ******************************************************/
angular.module('order.controller', ['order.service'])
  .controller('OrderCtrl', ['$scope', '$state','$ionicHistory','IndexedDBJs','CommonJs','OrderFty','CartFty', function ($scope, $state,$ionicHistory,IndexedDBJs,CommonJs,OrderFty,CartFty) {

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

  $scope.func_submitOrder = function (order) {
      order.addressId = $scope.consignee.id;
      var promise = OrderFty.submitOrder(order);
      promise.then(
          function (data) {
              CartFty.clearPay(order);
              var orderId = data['orderId'];
              var amount = data['amount'];
              window.location.href = "#/pay/"+orderId+"/"+amount;
          },
          function (e) {
              CommonJs.AlertPopup(e);
          }
      ).finally(function () {
      });
  };

    // 返回按钮方法
    $scope.func_goBack = function () {
      $ionicHistory.goBack();
    };

    $scope.func_goHome= function () {
      $state.go('tab.home');
    }
  }]);
