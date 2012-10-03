define(['angular'], function(angular) {
  var cpm = angular.module('cpm', []);

  cpm.config(['$routeProvider', function($routeProvider){
    $routeProvider.
      when('/', {controller: 'ListConceptProposalsCtrl', templateUrl: 'ListConceptProposals.html'}).
      when('/create', {controller: 'CreateConceptProposalCtrl', templateUrl: 'CreateConceptProposal.html'});
  }]);

  return cpm;
});
