define(['angular', 'config', 'filters', 'directives'], function(angular, config) {

  var cpm = angular.module('cpm', ['cpm.filters', 'cpm.directives']);

  cpm.config(['$routeProvider', function($routeProvider){
    $routeProvider.
      when('/', {controller: 'ListConceptProposalsCtrl', templateUrl: config.resourceLocation + '/ListConceptProposals.html'}).
      when('/edit', {controller: 'EditConceptProposalCtrl', templateUrl: config.resourceLocation + '/EditConceptProposal.html'}).
      when('/edit/:proposalId', {controller: 'EditConceptProposalCtrl', templateUrl: config.resourceLocation + '/EditConceptProposal.html'});
  }]);

  return cpm;
});
