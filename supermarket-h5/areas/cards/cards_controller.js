/******************************************************
 创建人：石国庆          创建时间：2015.10.20
 创建人：                修改时间：
 功  能：购物车
 ******************************************************/
angular.module('cards.controller', ['cards.service'])
  .controller('CardsCtrl', ['$scope', '$state','$ionicHistory','IndexedDBJs','CommonJs','CardsFty',function ($scope, $state,$ionicHistory,IndexedDBJs,CommonJs,CardsFty) {

      $scope.$on('$ionicView.beforeEnter', function (e) {
          initCardsInfo();
      });

      function initCardsInfo(){
          var promise = CardsFty.getUserCardsInfo();
          promise.then(
              function (data) {
                  var accountBindInfos = data;
                  if(accountBindInfos != null && accountBindInfos.length > 0){
                      for(var i in accountBindInfos){
                          if(accountBindInfos[i].accountType == 3){
                              $("#bankCardBind span").attr("class","mui-badge mui-badge-success");
                              $("#bankCardBind span").text("已绑定");
                          }else if(accountBindInfos[i].accountType == 2){
                              $("#aliPayBind span").attr("class","mui-badge mui-badge-success");
                              $("#aliPayBind span").text("已绑定");
                          }
                      }
                  } else {

                  }
              },
              function (e) {
                  mui.toast("获取用户卡包信息失败,请稍后再试",{'verticalAlign':'middle'});
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
