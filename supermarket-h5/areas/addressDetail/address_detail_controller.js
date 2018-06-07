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
      if(!$stateParams.addressId){
          return;
      }
      var promise=AddressDetailFty.addressDetail($stateParams.addressId);
      promise.then(
          function(data){
              if(data){
                  $scope.receiver = data;
              }else{

              }
          },
          function(err){

          }).finally(function() {

      });
  }

  $scope.func_saveCsnForm = function(){
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

    // 返回按钮方法
    $scope.func_goBack = function () {
      $ionicHistory.goBack();
    };

    $scope.func_goHome= function () {
      $state.go('tab.home');
    }
  }]);
