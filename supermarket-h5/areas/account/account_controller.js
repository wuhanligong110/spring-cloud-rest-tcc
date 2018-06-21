// 我的页面
angular.module('account.controller', ['account.service'])
  .controller('AccountCtrl',function($scope,$window,$http,$q,GlobalVariable,AccountFty) {

      initUserInfo();

      function initUserInfo(){
          var promise=AccountFty.initUserInfo();
          promise.then(
              function(data){
                  if(data){
                      $scope.user = data;
                  }else{

                  }
              },
              function(err){

              }).finally(function() {

          });
      }

      // 打电话
      $scope.func_callPhone=function(number){
        $window.location.href="tel:"+number;
      };

  });




