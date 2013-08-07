define(['./index', 'config'], function(services, config) {

  services.factory('Proposals', ['$resource', function($resource) {
    return $resource(
      config.contextPath + '/ws/cpm/proposals/:proposalId',
      { proposalId:'@id' },
      { update: {method: 'PUT'}, cache: true }
    );
  }]);

  services.factory('ProposalReviews', ['$resource', function($resource) {
    return $resource(config.contextPath + '/ws/cpm/proposalReviews/:proposalId', {proposalId: '@id'}, {update: {method: 'PUT'}});
  }]);

  services.factory('ProposalReviewConcepts', ['$resource', function($resource) {
    return $resource(config.contextPath + '/ws/cpm/proposalReviews/:proposalId/concepts/:conceptId', {conceptId: '@id'}, {update: {method: 'PUT'}});
  }]);

  services.factory('Settings', ['$resource', function($resource) {
    return $resource(config.contextPath + '/ws/cpm/settings');
  }]);

  return services;
});
