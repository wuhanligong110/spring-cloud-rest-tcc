// 商品列表页面
angular.module('orderList.controller', ['orderList.service'])
  .controller('OrderListCtrl', function ($scope,$stateParams,OrderListFty,$ionicLoading,$ionicHistory) {

    // 初始化变量
    $scope.obj_orderListData=[];
    $scope.pms_isMoreItemsAvailable=true;

    // 分页查询对象
    $scope.obj_pagingInfo = {
      amountMax: "",
      amountMin: "",
      billNum: "",
      createUser: "",
      dateFrom: "",
      dateTo: "",
      deptID: "",
      deptName: "",
      keyWord: "",
      loginName: "",
      billType: "",
      pageNo: 1,
      pageSize: 10,
      sortFlag: "0",
      sortType: "desc",
      orderStatus:$stateParams.orderStatus
    };

    // 视图事件
    $scope.$on('$ionicView.beforeEnter', function (e) {
      $scope.func_refreshOrderList();
    });

    // 获取订单最新列表数据
    $scope.func_refreshOrderList=function(){
      // 要传递的参数
      $scope.obj_pagingInfo.pageNo=1;
      var message=$scope.obj_pagingInfo;
      // 调用promise对象
      var promise=OrderListFty.refreshOrderList(message);
      promise.then(
        function(data){
          if(data.list){
            $scope.obj_orderListData=data.list;
          }
          if(!data.lastPage){
            $scope.pms_isMoreItemsAvailable=true;
          }else{
            $scope.pms_isMoreItemsAvailable=false;
          }
        },
        function(err){

        }).finally(function() {
          // 停止广播ion-refresher
          $scope.$broadcast('scroll.refreshComplete');
          $scope.$broadcast('scroll.infiniteScrollComplete');
      });
    };

    // 加载更多数据的方法
    $scope.func_loadMoreOrderList=function(){

      console.log("loading............");

      $ionicLoading.show({
        template: 'loading....'
      });

      // 要传递的参数
      $scope.obj_pagingInfo.pageNo++;
      var message=$scope.obj_pagingInfo;
      // 调用promise对象
      var promise=OrderListFty.refreshOrderList(message);
      promise.then(
        function(data){
          if(!data.lastPage){
            $scope.pms_isMoreItemsAvailable=true;
            $.each(data.list, function(i, item) {
              $scope.obj_orderListData.push(item);
            });
            console.log($scope.obj_orderListData);
          }else{
            $scope.pms_isMoreItemsAvailable=false;
          }
        },
        function(err){

        }).finally(function() {

          // 停止加载更多方法的广播
          $scope.$broadcast('scroll.infiniteScrollComplete');
          setTimeout(function(){
            $ionicLoading.hide();
          },1000)

      });
    };

    // 返回方法
    $scope.func_goBack=function(){
      $ionicHistory.goBack();
    }

  });
