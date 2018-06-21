// 商品列表页面
angular.module('orderDetail.controller', ['orderDetail.service'])
  .controller('OrderDetailCtrl', function ($scope,$stateParams,OrderDetailFty,$ionicLoading,$ionicHistory) {

      orderDetail();

      function orderDetail() {
          var promise=OrderDetailFty.orderDetail($stateParams.orderId);
          promise.then(
              function(data){
                  if(data){
                      $scope.order = data;
                  }else{

                  }
              },
              function(err){

              }).finally(function() {

          });

      }

      $scope.func_hasReceived = function (orderId) {
          var promise=OrderDetailFty.hasReceived(orderId);
          promise.then(
              function(data){
                  if(data == 20000){
                      history.go(-1);
                  }
              },
              function(err){

              }).finally(function() {

          });
      };

      $scope.func_orderCancel = function (orderId) {
          var promise=OrderDetailFty.orderCancel(orderId);
          promise.then(
              function(data){
                  if(data == 20000){
                      history.go(-1);
                  }
              },
              function(err){

              }).finally(function() {

          });
      };

    // 返回方法
    $scope.func_goBack=function(){
      $ionicHistory.goBack();
    }

  });
