define(['angular'], function(angular) {
  var cpm = angular.module('cpm', []);

  cpm.config(['$routeProvider', function($routeProvider){
    $routeProvider.
      when('/', {controller: 'ListConceptProposalsCtrl', templateUrl: '/openmrs/moduleResources/cpm/ListConceptProposals.html'}).
      when('/create', {controller: 'CreateConceptProposalCtrl', templateUrl: '/openmrs/moduleResources/cpm/CreateConceptProposal.html'});
  }]);

  return cpm;
});
