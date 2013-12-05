define([
    'angular',
    'config',
    './index'
  ],
  function(angular, config) {

    'use strict';

    angular.module('cpmr.services').factory('ProposalReviewConcepts',
      function($resource) {
        return $resource(
          config.contextPath + '/ws/conceptreview/proposalReviews/:proposalId/concepts/:conceptId',
          {conceptId: '@id'},
          {update: {method: 'PUT'}});
      }
    );
  }
);