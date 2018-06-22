/******************************************************
 创建人：石国庆          创建时间：2015.10.20
 创建人：                修改时间：
 功  能：购物车
 ******************************************************/
angular.module('bankcard.controller', ['bankcard.service'])
  .controller('BankcardCtrl', ['$scope', '$state','$ionicHistory','IndexedDBJs','CommonJs','BankcardFty', function ($scope, $state,$ionicHistory,IndexedDBJs,CommonJs,BankcardFty) {

      $scope.$on('$ionicView.beforeEnter', function (e) {
          initBankInfo();
          initUserBankCardInfo();
      });

      function initUserBankCardInfo() {
          var promise = BankcardFty.getUserBankCardInfo();
          promise.then(
              function (data) {
                  var accountBindInfo = data;
                  console.log(accountBindInfo);
                  if(accountBindInfo){
                      $("#username").val(accountBindInfo.userName);
                      $("#account").val(accountBindInfo.accountCard);
                      console.log(accountBindInfo.accountCard);
                      $("#openBank").attr("txt",accountBindInfo.accountCode);
                      $("#openBank").val(accountBindInfo.accountName);
                      $("#mobile").val(accountBindInfo.reserveMobile);
                      $("#bandAccount").attr("disabled","disabled");
                      $("#bandAccount").text("已绑定");
                  } else {

                  }
              },
              function (e) {
                  CommonJs.AlertPopup(e);
              }
          ).finally(function () {
          });
      }

      function initBankInfo() {

          //普通示例
          var bankPicker = new mui.PopPicker();
          bankPicker.setData([{
              value: '1002',
              text: '工商银行'
          }, {
              value: '1005',
              text: '农业银行'
          }, {
              value: '1026',
              text: '中国银行'
          }, {
              value: '1003',
              text: '建设银行'
          }, {
              value: '1001',
              text: '招商银行'
          }, {
              value: '1066',
              text: '邮储银行'
          }, {
              value: '1020',
              text: '交通银行'
          }, {
              value: '1004',
              text: '浦发银行'
          }, {
              value: '1006',
              text: '民生银行'
          }, {
              value: '1009',
              text: '兴业银行'
          }, {
              value: '1010',
              text: '平安银行'
          }, {
              value: '1021',
              text: '中信银行'
          }, {
              value: '1025',
              text: '华夏银行'
          }, {
              value: '1027',
              text: '广发银行'
          }, {
              value: '1022',
              text: '光大银行'
          }, {
              value: '1032',
              text: '北京银行'
          }, {
              value: '1056',
              text: '宁波银行'
          }]);

          var showBankPickerButton = document.getElementById('showBankPicker');
          var openBank = document.getElementById('openBank');
          showBankPickerButton.addEventListener('tap', function(event) {
              bankPicker.show(function(items) {
                  openBank.value = items[0].text;
                  openBank.setAttribute("txt",items[0].value);
              });
          }, false);

      }

      //检验送货地址
      function checkPayeeAccount(username,account,openBank,mobile,bankCode){
          var regex = /^1[3|4|5|7|8][0-9]{9}$/;
          var accountReg = /^([1-9]{1})(\d{14}|\d{18})$/;
          if(username == ''){
              mui.toast("用户名称不能为空",{'verticalAlign':'middle'});
              return false;
          }
          if(username.length > 20){
              mui.toast("用户名称不能超过20个字符",{'verticalAlign':'middle'});
              return false;
          }
          if(mobile == ''){
              mui.toast("手机号码不能为空",{'verticalAlign':'middle'});
              return false;
          }
          if(!regex.test(mobile)){
              mui.toast("请输入正确的手机号码",{'verticalAlign':'middle'});
              return false;
          }
          if(openBank == ''){
              mui.toast("开户行不能为空",{'verticalAlign':'middle'});
              return false;
          }
          if(account == ''){
              mui.toast("银行账号不能为空",{'verticalAlign':'middle'});
              return false;
          }
          if(!accountReg.test(account)){
              mui.toast("请输入正确的银行账号",{'verticalAlign':'middle'});
              return false;
          }
          if(bankCode == ''){
              mui.toast("银行编码不能为空",{'verticalAlign':'middle'});
              return false;
          }
          return true;
      }

      $scope.func_bankcard = function () {
          var account = $("#account").val();
          var username = $("#username").val();
          var openBank = $("#openBank").val();
          var bankCode = $("#openBank").attr("txt");
          var mobile = $("#mobile").val();
          if(checkPayeeAccount(username,account,openBank,mobile,bankCode)){
              var btnArray = ['否', '是'];
              mui.confirm('请仔细核对相关信息，绑定后无法修改！确认无误？', '绑卡信息确认', btnArray, function(e) {
                  if (e.index == 1) {
                      var bindRequest = {account_card:account,user_name:username,account_name:openBank,reserve_mobile:mobile,account_code:bankCode,account_type:3};
                      var promise = BankcardFty.bindBankCard(bindRequest);
                      promise.then(
                          function (data) {
                              if(data == 20000){
                                  mui.toast("绑卡成功",{'verticalAlign':'middle'});
                                  window.location.href = '/';
                              }else{
                                  mui.toast("绑卡异常,请联系管理员",{'verticalAlign':'middle'});
                              }
                          },
                          function (e) {
                              CommonJs.AlertPopup(e);
                          }
                      ).finally(function () {
                      });
                  } else {

                  }
              });

          }
      }

    // 返回按钮方法
    $scope.func_goBack = function () {
      $ionicHistory.goBack();
    };

    $scope.func_goHome= function () {
      $state.go('tab.home');
    }
  }]);
