/******************************************************
 创建人：石国庆          创建时间：2015.10.20
 创建人：                修改时间：
 功  能：购物车
 ******************************************************/
angular.module('pay.controller', ['pay.service'])
  .controller('PayCtrl', ['$scope', '$state','$stateParams','$ionicHistory','IndexedDBJs','CommonJs','PayFty',function ($scope, $state,$stateParams,$ionicHistory,IndexedDBJs,CommonJs,PayFty) {

  $scope.$on('$ionicView.beforeEnter', function (e) {
      getPayParams();
  });

  function getPayParams() {
      $scope.amount = $stateParams.amount;
      $scope.orderId = $stateParams.orderId;
  }

  $scope.func_pay = function (orderId) {
      var promise = PayFty.pay(orderId);
      promise.then(
          function (returnParams) {
              var errorMessage = returnParams['errorMessage'];
              if(errorMessage == '' || errorMessage == null){
                  var appId = returnParams['appId'];
                  var timeStamp = returnParams['timeStamp'];
                  var nonceStr = returnParams['nonceStr'];
                  var package = returnParams['package'];
                  var signType = returnParams['signType'];
                  var paySign = returnParams['paySign'];
                  console.log("调用微信统一下单接口  appId="+appId+"	 timeStamp="+timeStamp+" nonceStr="+nonceStr+"	package="+package+" signType="+signType+" paySign="+paySign);
                  var prepayId = returnParams['prepayId'];
                  if(prepayId == ''){
                      mui.toast("微信支付失败,请稍后再试",{'verticalAlign':'middle'});
                  } else {
                      mui.toast("微信支付开发中,敬请期待！",{'verticalAlign':'middle'});
                      window.location.href = "#/home";
                      WeixinJSBridge.invoke(
                          'getBrandWCPayRequest', {
                              "appId" : appId,     //公众号名称，由商户传入
                              "timeStamp": timeStamp, //时间戳，自1970年以来的秒数
                              "nonceStr" : nonceStr, //随机串
                              "package" : package,   //预付商品id 微信格式数据包
                              "signType" : signType,  //微信签名方式
                              "paySign" : paySign   //微信签名
                          },

                          function(res){
                              if(res.err_msg == "get_brand_wcpay_request:ok" ) {
                                  mui.alert("恭喜您支付成功","支付结果","确定",function(){
                                      window.location.href = '#/home';
                                  },'div')
                              }     // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
                          }
                      );
                  }
                  window.location.href = "#/home";
              } else {
                  mui.alert(errorMessage,"支付失败","确定",null,'div')
              }
          },
          function (e) {
              //CommonJs.AlertPopup(e);
              mui.toast("微信支付失败,请稍后再试",{'verticalAlign':'middle'});
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
