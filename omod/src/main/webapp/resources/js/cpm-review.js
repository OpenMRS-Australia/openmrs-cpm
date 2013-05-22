define(['angular', 'config', 'js/filters', 'js/directives/jqueryUiDialog', 'js/directives/cpmKeyup', 'js/services'], function(angular, config) {

  var cpm = angular.module('cpm-review', ['cpm.filters', 'cpm.directives', 'cpm.services']);

  cpm.config(['$routeProvider', function($routeProvider){
    $routeProvider.
      when('/', {controller: 'ListIncomingProposalsCtrl', templateUrl: config.resourceLocation + '/partials/ListIncomingProposals.html'}).
      when('/edit/:proposalId', {controller: 'ReviewProposalCtrl', templateUrl: config.resourceLocation + '/partials/ReviewProposal.html'}).
      when('/edit/:proposalId/concept/:conceptId', {controller: 'ReviewConceptCtrl', templateUrl: config.resourceLocation + '/partials/ReviewConcept.html'});

  }]).run(['$rootScope', '$location', function($rootScope, $location) {

      $rootScope.isActiveTab = function(page, route) {
          return $location.absUrl().indexOf(page + '.list') > 0 && $location.path() == route;
      };
  }]);

  return cpm;
});