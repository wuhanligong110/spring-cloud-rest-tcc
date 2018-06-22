/******************************************************
 创建人：石国庆          创建时间：2015.10.20
 创建人：                修改时间：
 功  能：购物车
 ******************************************************/
angular.module('address.detail.controller', ['address.detail.service'])
  .controller('AddressDetailCtrl', ['$scope', '$state','$ionicHistory','IndexedDBJs','CommonJs','AddressDetailFty','$stateParams', function ($scope, $state,$ionicHistory,IndexedDBJs,CommonJs,AddressDetailFty,$stateParams) {

  $scope.receiver = {};

  $scope.$on('$ionicView.beforeEnter', function (e) {
      addressDetail();
  });

  function addressDetail(){
      var _getParam = function(obj, param) {
          return obj[param] || '';
      };

      var cityPicker3 = new mui.PopPicker({
          layer: 3
      });
      cityPicker3.setData(cityData3);
      var showCityPickerButton = document.getElementById('showCityPicker3');
      var cityResult3 = document.getElementById('cityResult3');
      showCityPickerButton.addEventListener('tap', function(event) {
          cityPicker3.show(function(items) {
              cityResult3.value = _getParam(items[0], 'text') + " " + _getParam(items[1], 'text') + " " + _getParam(items[2], 'text');
              $scope.receiver.provinceName = _getParam(items[0], 'text');
              $scope.receiver.cityName = _getParam(items[1], 'text');
              $scope.receiver.area = _getParam(items[2], 'text');
              //返回 false 可以阻止选择框的关闭
              //return false;
          });
      }, false);
      if(!$stateParams.addressId){
          return;
      }
      var promise=AddressDetailFty.addressDetail($stateParams.addressId);
      promise.then(
          function(data){
              if(data){
                  $scope.receiver = data;
                  document.getElementById('cityResult3').value = $scope.receiver.provinceName + " " + $scope.receiver.cityName + " " + $scope.receiver.area;
              }else{

              }
          },
          function(err){

          }).finally(function() {

      });
  }

  $scope.func_saveCsnForm = function(){
      var username = $scope.receiver.receivingUserName;
      var phone = $scope.receiver.mobile;
      var address = $scope.receiver.receivingAddress;
      var area = $scope.receiver.provinceName + " " + $scope.receiver.cityName + " " + $scope.receiver.area;
      if(checkAddress(username,phone,address,area)){
          var promise = AddressDetailFty.saveCsnForm($scope.receiver);
          promise.then(
              function (data) {
                  if(data){
                      history.go(-1);
                  }
              },
              function (e) {
                  CommonJs.AlertPopup(e);
              }
          ).finally(function () {
          });
      }
  };

  $scope.func_deleteCsnForm = function(addressId){
      var promise = AddressDetailFty.deleteCsnForm(addressId);
      promise.then(
          function (data) {
              if(data == 20000){
                  history.go(-1);
              }
          },
          function (e) {
              CommonJs.AlertPopup(e);
          }
      ).finally(function () {
      });
  };

  function checkAddress(username,phone,address,area){
      var regex = /^1[3|4|5|7|8][0-9]{9}$/;
      console.log("username = " +username+";phone = "+phone+";address = "+address+";area="+area);
      if(username == '' || username == undefined){
          mui.toast("邮寄地址姓名不能为空",{'verticalAlign':'middle'});
          return false;
      }
      if(username.length > 20){
          mui.toast("邮寄地址姓名不能超过20个字符",{'verticalAlign':'middle'});
          return false;
      }
      if(phone == '' || phone == undefined){
          mui.toast("邮寄地址电话不能为空",{'verticalAlign':'middle'});
          return false;
      }
      if(!regex.test(phone)){
          mui.toast("请输入正确的手机号码",{'verticalAlign':'middle'});
          return false;
      }
      if(address == '' || address == undefined ){
          mui.toast("邮寄地址不能为空",{'verticalAlign':'middle'});
          return false;
      }
      if(area.trim() == '' || area == 'undefined undefined undefined'){
          mui.toast("邮寄地址地区不能为空",{'verticalAlign':'middle'});
          return false;
      }
      if(area.length > 20){
          mui.toast("邮寄地址地区不能超过20个字符",{'verticalAlign':'middle'});
          return false;
      }
      return true;
  }

    // 返回按钮方法
    $scope.func_goBack = function () {
      $ionicHistory.goBack();
    };

    $scope.func_goHome= function () {
      $state.go('tab.home');
    }
  }]);
