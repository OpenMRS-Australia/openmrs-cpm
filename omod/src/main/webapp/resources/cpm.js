define(['angular', 'config', 'filters', 'directives', 'services'], function(angular, config) {

  var cpm = angular.module('cpm', ['cpm.filters', 'cpm.directives', 'cpm.services']);

  cpm.config(['$routeProvider', function($routeProvider){
    $routeProvider.
      when('/', {controller: 'ListProposalsCtrl', templateUrl: config.resourceLocation + '/partials/ListProposals.html'}).
      when('/edit', {controller: 'EditProposalCtrl', templateUrl: config.resourceLocation + '/partials/EditProposal.html'}).
      when('/edit/:proposalId', {controller: 'EditProposalCtrl', templateUrl: config.resourceLocation + '/partials/EditProposal.html'}).
      when('/settings', {controller: 'SettingsCtrl', templateUrl: config.resourceLocation + '/partials/Settings.html'});

  }]).run(['$rootScope', '$location', function($rootScope, $location) {

      $rootScope.isActiveTab = function(page, route) {
          return $location.absUrl().indexOf(page + '.list') > 0 && $location.path() == route;
      };
  }]);

  return cpm;
});