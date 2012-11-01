define(['angular', 'angular-resource'], function(angular) {

  var services = angular.module('cpm.services', ['ngResource']);

  services.factory('Proposals', ['$resource', function($resource) {
    return $resource('/openmrs/ws/cpm/proposals/:proposalId', {proposalId:'@id'}, {update: {method: 'PUT'}});
  }]);

  return services;
});
