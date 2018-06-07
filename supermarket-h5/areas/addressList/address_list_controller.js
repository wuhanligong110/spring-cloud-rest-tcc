/******************************************************
 创建人：石国庆          创建时间：2015.10.20
 创建人：                修改时间：
 功  能：购物车
 ******************************************************/
angular.module('address.list.controller', ['address.list.service'])
  .controller('AddressListCtrl', ['$scope', '$state','$ionicHistory','IndexedDBJs','CommonJs','AddressListFty', function ($scope, $state,$ionicHistory,IndexedDBJs,CommonJs,AddressListFty,$location) {

  $scope.$on('$ionicView.beforeEnter', function (e) {
      getConsigneeList();
  });

  function getConsigneeList(){
      var promise = AddressListFty.getConsigneeList();
      promise.then(
          function (data) {
              $scope.consigneeList = data;
          },
          function (e) {
              CommonJs.AlertPopup(e);
          }
      ).finally(function () {
      });
  }

  $scope.func_changeCsn = function(addressId){
      var promise = AddressListFty.setDefault(addressId);
      promise.then(
          function (data) {
              window.location = "#/order";
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
