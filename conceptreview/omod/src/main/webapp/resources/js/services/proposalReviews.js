define([
    'angular',
    'config',
    './index'
  ],
  function(services, config) {

    'use strict';

    angular.module('cpmr.services').factory('ProposalReviews',
      function($resource) {
        return $resource(
          config.contextPath + '/ws/cpm/proposalReviews/:proposalId',
          {proposalId: '@id'},
          {update: {method: 'PUT'}});
      }
    );
  }
);