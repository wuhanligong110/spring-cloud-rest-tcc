angular.module('address.list.route', ['address.list.controller'])
  .config(function ($stateProvider, $urlRouterProvider) {

    $stateProvider
      .state('addressList', {
        url: '/address/list',
        templateUrl: 'areas/addressList/address_list.html',
        controller: 'AddressListCtrl'
      })
  });
