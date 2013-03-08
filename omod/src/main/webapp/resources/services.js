define(['angular', 'config', 'angular-resource'], function(angular, config) {

  var services = angular.module('cpm.services', ['ngResource']);

  services.factory('Proposals', ['$resource', function($resource) {
    return $resource(config.contextPath + '/ws/cpm/proposals/:proposalId', {proposalId:'@id'}, {update: {method: 'PUT'}});
  }]);

  services.factory('ProposalResponses', ['$resource', function($resource) {
    return $resource(config.contextPath + '/ws/cpm/proposalResponses/:responseId', {responseId: '@id'}, {update: {method: 'PUT'}});
  }]);

  services.factory('Settings', ['$resource', function($resource) {
    return $resource(config.contextPath + '/ws/cpm/settings');
  }]);

  return services;
});
