define(['angular', 'config', 'angular-resource'], function(angular, config) {

  var services = angular.module('cpm.services', ['ngResource']);

  services.factory('Proposals', ['$resource', function($resource) {
  	//http PUT doesnt work as we need to use Spring's HiddenHttpMethodFilter on web.xml on the war
  	//using POST for now (http://blog.springsource.com/2009/03/08/rest-in-spring-3-mvc/ )
    return $resource(config.contextPath + '/ws/cpm/proposals/:proposalId', {proposalId:'@id'}, {update: {method: 'POST'}});
  }]);

  services.factory('ProposalResponses', ['$resource', function($resource) {
    return $resource(config.contextPath + '/ws/cpm/proposalResponses/:responseId', {responseId: '@id'}, {update: {method: 'PUT'}});
  }]);

  return services;
});
