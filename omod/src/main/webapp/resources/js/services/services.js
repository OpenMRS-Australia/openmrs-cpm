define(['./index', 'config'], function(services, config) {

  'use strict';

  services.factory('Proposals', function($resource) {
    return $resource(
      config.contextPath + '/ws/cpm/proposals/:proposalId',
      { proposalId:'@id' },
      { update: {method: 'PUT'}, cache: true }
    );
  });
  
  services.factory('CreateProposals', function($resource) {
    return $resource(
      config.contextPath + '/ws/cpm/proposals/create',
      { update: {method: 'PUT'}, cache: true }
    );
  });

  services.factory('ProposalReviews', function($resource) {
    return $resource(
      config.contextPath + '/ws/cpm/proposalReviews/:proposalId',
      {proposalId: '@id'},
      {update: {method: 'PUT'}});
  });

  services.factory('ProposalReviewConcepts', function($resource) {
    return $resource(
      config.contextPath + '/ws/cpm/proposalReviews/:proposalId/concepts/:conceptId',
      {conceptId: '@id'},
      {update: {method: 'PUT'}});
  });

  services.factory('Settings', function($resource) {
    return $resource(config.contextPath + '/ws/cpm/settings');
  });

  return services;
});