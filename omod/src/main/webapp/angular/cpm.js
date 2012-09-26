angular.module('cpm', []).config(function($routeProvider){
  $routeProvider.when('/', {controller: CreateConceptProposalCtrl, templateUrl: 'CreateConceptProposal.html'});
})
