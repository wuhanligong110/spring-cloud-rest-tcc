// 商品列表页面
angular.module('orderDetail.controller', ['orderDetail.service'])
  .controller('OrderDetailCtrl', function ($scope,$stateParams,OrderDetailFty,$ionicLoading,$ionicHistory) {

      orderDetail();

      function orderDetail() {
          var promise=OrderDetailFty.orderDetail($stateParams.orderId);
          promise.then(
              function(data){
                  if(data){
                      console.log(data);
                      $scope.order = data;
                  }else{

                  }
              },
              function(err){

              }).finally(function() {

          });

      }

    // 返回方法
    $scope.func_goBack=function(){
      $ionicHistory.goBack();
    }

  });
