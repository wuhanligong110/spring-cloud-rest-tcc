/******************************************************
 创建人：石国庆          创建时间：2015.10.20
 创建人：                修改时间：
 功  能：购物车
 ******************************************************/
angular.module('withdraw.controller', ['withdraw.service'])
  .controller('WithdrawCtrl', ['$scope', '$state','$stateParams','$ionicHistory','IndexedDBJs','CommonJs','CardsFty','WithdrawFty',function ($scope, $state,$stateParams,$ionicHistory,IndexedDBJs,CommonJs,CardsFty,WithdrawFty) {

      $scope.$on('$ionicView.beforeEnter', function (e) {
          initWithdrawInfo();
          initCardsInfo();
      });

      function initWithdrawInfo(){
          $scope.withdrawCredit = $stateParams.withdrawCredit;
      }

      function initCardsInfo(){
          var promise = CardsFty.getUserCardsInfo();
          promise.then(
              function (data) {
                  var accountBindInfos = data;
                  if(accountBindInfos != null && accountBindInfos.length > 0){
                      var userAccountArray = new Array();
                      for(var i in accountBindInfos){
                          var userAccountTemp = {value: accountBindInfos[i].id, text: accountBindInfos[i].accountName + '(' + accountBindInfos[i].accountCard.substring(accountBindInfos[i].accountCard.length-5,accountBindInfos[i].accountCard.length-1) + ')'};
                          userAccountArray.push(userAccountTemp);
                          if(i == 1){
                              $("#userAccount").attr("txt",userAccountTemp.value);
                              $("#userAccount").val(userAccountTemp.text);
                          }
                      }

                      var accountPicker = new mui.PopPicker();
                      accountPicker.setData(userAccountArray);

                      var showAccountPickerButton = document.getElementById('showAccountPicker');
                      var userAccount = document.getElementById('userAccount');
                      showAccountPickerButton.addEventListener('tap', function(event) {
                          accountPicker.show(function(items) {
                              userAccount.value = items[0].text;
                              userAccount.setAttribute("txt",items[0].value);
                          });
                      }, false);
                  } else {

                  }
              },
              function (e) {
                  mui.toast("获取用户卡包信息失败,请稍后再试",{'verticalAlign':'middle'});
              }
          ).finally(function () {
          });
      }

      $scope.func_withdraw = function(){
          var amount = $("#withdrawAmount").val();
          var userAccount = $("#userAccount").attr("txt");
          if(checkWithdraw(amount,userAccount)){
              WithdrawFty.withdraw();
              var withdrawRequest = {amount:amount,userAccount:userAccount};
              var promise = WithdrawFty.withdraw(withdrawRequest);
              promise.then(
                  function (data) {
                      if(data == 20000){
                          mui.toast("提现成功",{'verticalAlign':'middle'});
                      }else{
                          mui.toast("服务异常,请联系管理员",{'verticalAlign':'middle'});
                      }
                  },
                  function (e) {
                      mui.toast("服务异常,请联系管理员",{'verticalAlign':'middle'});
                  }
              ).finally(function () {
              });
          }
      };

      function checkWithdraw(amount,userAccount){
          if(!userAccount){
              var btnArray = ['否', '是'];
              mui.confirm('您还未进行账户绑定，确认前往绑定？', '绑卡确认', btnArray, function(e) {
                  if (e.index == 1) {
                      window.location.href = '#/bankcard';
                      return false;
                  } else {
                      return false;
                  }
              });
          } else {
              //		mui.toast("系统试运营阶段，暂不支持领取",{'verticalAlign':'middle'});
              //		return false;
              var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
              //获取可领取积分
              var cashCredit = $scope.withdrawCredi;
              //领取积分
              if(amount == ""){
                  mui.toast("请输入提现金额",{'verticalAlign':'middle'});
                  return false;
              } else {
                  if(!reg.test(amount)){
                      mui.toast("请输入正确的提现金额",{'verticalAlign':'middle'});
                      return false;
                  }
                  var remainCashCredit = eval(cashCredit-amount);
                  if(amount <= 0){
                      mui.toast("提现金额必须大于0",{'verticalAlign':'middle'});
                      return false;
                  } else if(remainCashCredit < 0){
                      mui.toast("超过了可提现金额,无法提现",{'verticalAlign':'middle'});
                      return false;
                  } else {
                      $("#withdrawAmount").val('');
                      //将按钮变成灰色不可点击
                      $("#withdrawButton").attr("disabled",true);
                      return true;
                  }
              }
          }
      }

      $scope.func_withdrawAll = function(){
          $("#withdrawAmount").val($scope.withdrawCredit);
      };

    // 返回按钮方法
    $scope.func_goBack = function () {
      $ionicHistory.goBack();
    };

    $scope.func_goHome= function () {
      $state.go('tab.home');
    }
  }]);
