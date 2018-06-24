// 积分列表页面
angular.module('creditList.controller', ['creditList.service'])
  .controller('CreditListCtrl', function ($scope,$stateParams,CreditListFty,$ionicLoading,$ionicHistory) {

    // 初始化变量
    $scope.obj_creditListData=[];
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
      typeCategory:$stateParams.creditCategory,
      typeValue:$stateParams.creditType
    };

    // 视图事件
    $scope.$on('$ionicView.beforeEnter', function (e) {
      $scope.func_refreshCreditList();
    });

    // 获取订单最新列表数据
    $scope.func_refreshCreditList=function(){
      // 要传递的参数
      $scope.obj_pagingInfo.pageNo=1;
      var message=$scope.obj_pagingInfo;
      // 调用promise对象
      var promise=CreditListFty.refreshCreditList(message);
      promise.then(
        function(data){
          if(data.list){
            $scope.obj_creditListData=data.list;
          }
          if(!data.lastPage && data.list.length > 0){
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
    $scope.func_loadMoreCreditList=function(){

      console.log("loading............");

      $ionicLoading.show({
        template: 'loading....'
      });

      // 要传递的参数
      $scope.obj_pagingInfo.pageNo++;
      var message=$scope.obj_pagingInfo;
      // 调用promise对象
      var promise=CreditListFty.refreshCreditList(message);
      promise.then(
        function(data){
          if(!data.lastPage){
            $scope.pms_isMoreItemsAvailable=true;
            $.each(data.list, function(i, item) {
              $scope.obj_creditListData.push(item);
            });
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
