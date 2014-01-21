define([
    'angular',
    'config',
    './index'
  ],
  function(angular, config) {

    'use strict';

    angular.module('conceptreview.services').factory('ProposalReviewConcepts',
      function($resource) {
        return $resource(
          config.contextPath + '/ws/conceptreview/proposalReviews/:proposalId/concepts/:conceptId',
          {conceptId: '@id'},
          {update: {method: 'PUT'}});
      }
    );
  }
);