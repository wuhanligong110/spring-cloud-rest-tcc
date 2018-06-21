angular.module('cards.route', ['cards.controller'])
  .config(function ($stateProvider, $urlRouterProvider) {

    $stateProvider
      .state('cards', {
        url: '/cards',
        templateUrl: 'areas/cards/cards.html',
        controller: 'CardsCtrl'
      })
  });
