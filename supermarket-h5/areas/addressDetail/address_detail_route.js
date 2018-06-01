angular.module('address.detail.route', ['address.detail.controller'])
  .config(function ($stateProvider, $urlRouterProvider) {

    $stateProvider
      .state('addressDetail', {
        url: '/address/detail/:addressId',
        templateUrl: 'areas/addressDetail/address_detail.html',
        controller: 'AddressDetailCtrl'
      })
  });
