/******************************************************
 创建人：石国庆          创建时间：2015.10.20
 创建人：                修改时间：
 功  能：购物车
 ******************************************************/
angular.module('cart.controller', ['cart.service'])
  .controller('CartCtrl', ['$scope', '$state','$ionicHistory','IndexedDBJs','CommonJs','CartFty', function ($scope, $state,$ionicHistory,IndexedDBJs,CommonJs,CartFty) {

    // 获取所有数据
    $scope.$on('$ionicView.beforeEnter', function (e) {
      func_getAllData();
    });

    // 获取indexdb的数据
    $scope.obj_cartDbData={
      data:"",
      total:0
    }

    // 获取全部数据
    function func_getAllData(){
      var promise = CartFty.getAllData();
      promise.then(
        function (data) {
          var total=0;
          // 绑定要循环生成的列表数据对象
          $scope.obj_cartDbData.data=data;
          console.log("this is data");
          console.log(data);
          // 计算总金额
          for(var i=0;i<data.length;i++){
            for(var j=0;j<data[i].businessProductList.length;j++){
                total=total+parseFloat(data[i].businessProductList[j].price)*data[i].businessProductList[j].number*1.0;
            }
          }
          $scope.obj_cartDbData.total=total.toFixed(2);
          //异步需要手动调用
          //$scope.$digest();
        },
        function (e) {
          CommonJs.AlertPopup(e);
        }
      ).finally(function () {
        });
    }

    // 数量加1
    $scope.func_jia1=function(businessId,productId){
      var promise = CartFty.get(businessId);
      promise.then(
        function (data) {
            for(var i in data.businessProductList){
                if(data.businessProductList[i].productId == productId){
                    if(data.businessProductList[i].number != 1){
                        data.businessProductList[i].number++;
                        func_updateData(data);
                    }
                }
            }
        },
        function (e) {
          CommonJs.AlertPopup(e);
        }
      ).finally(function () {
        });
    }

    // 数量减1
    $scope.func_jian1=function(businessId,productId){
      var promise = CartFty.get(businessId);
      promise.then(
        function (data) {
          for(var i in data.businessProductList){
            if(data.businessProductList[i].productId == productId){
                if(data.businessProductList[i].number != 1){
                    data.businessProductList[i].number--;
                    func_updateData(data);
                }
            }
          }
        },
        function (e) {
          CommonJs.AlertPopup(e);
        }
      ).finally(function () {
        });
    }

    // 删除
    $scope.func_delete=function(businessId,productId){
        console.log(businessId);
        console.log(productId);
        console.log("delete");
        var promise = CartFty.get(businessId);
        promise.then(
          function (data) {
              console.log("data");
              console.log(data);
              for(var i in data.businessProductList){
                  if(data.businessProductList[i].productId == productId){
                      data.businessProductList = data.businessProductList.slice(0,i).concat(data.businessProductList.slice(i+1,data.businessProductList.length));
                      if(data.businessProductList.length == 0){
                          CartFty.delete(businessId);
                      }
                      func_updateData(data);
                  }
              }
          },
          function (e) {
            CommonJs.AlertPopup(e);
          }
        ).finally(function () {
          });
    }
      
    // 保存数据
    function func_updateData(data){
      var promise = CartFty.updateData(data);
      promise.then(
        function () {
          func_getAllData();
        },
        function (e) {
          CommonJs.AlertPopup(e);
        }
      ).finally(function () {
        });
    }
    //
    //
    //// 选择被选中的按钮的jquery语句
    ////$("input[type='checkbox']:checked").each(function(){
    ////  console.log($(this).val());
    ////  $scope.batchApproveInfo.taskIDS=$scope.batchApproveInfo.taskIDS+$(this).val()+","
    ////});

    // 返回按钮方法
    $scope.func_goBack = function () {
      $ionicHistory.goBack();
    };

    $scope.func_goHome= function () {
      $state.go('tab.home');
    }
  }]);
