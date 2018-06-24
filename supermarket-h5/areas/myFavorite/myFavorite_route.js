angular.module('myFavorite.route', ['myFavorite.controller'])
  .config(function ($stateProvider, $urlRouterProvider) {

    $stateProvider
      .state('myFavorite', {
        url: '/myFavorite',
        templateUrl: 'areas/myFavorite/myFavorite.html',
        controller: 'MyFavoriteCtrl'
      })
  });
