define([
    'angular',
    'config',
    './index'
  ],
  function(angular, config) {

    'use strict';

    angular.module('conceptreview.services').factory('DeletedProposalReviewConcepts',
      function($resource) {
        return $resource(
          config.contextPath + '/ws/conceptreview/deletedProposalReviews/:proposalId/concepts/:conceptId',
          {conceptId: '@id'},
          {update: {method: 'PUT'}});
      }
    );
  }
);