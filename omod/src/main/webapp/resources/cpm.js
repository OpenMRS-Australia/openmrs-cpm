define(['angular'], function(angular) {
  var cpm = angular.module('cpm', []);

  cpm.config(['$routeProvider', function($routeProvider){
    $routeProvider.
      when('/', {controller: 'ListConceptProposalsCtrl', templateUrl: '/openmrs/moduleResources/cpm/ListConceptProposals.html'}).
      when('/edit', {controller: 'EditConceptProposalCtrl', templateUrl: '/openmrs/moduleResources/cpm/EditConceptProposal.html'}).
      when('/edit/:proposalId', {controller: 'EditConceptProposalCtrl', templateUrl: '/openmrs/moduleResources/cpm/EditConceptProposal.html'});
  }]);

  return cpm;
});
