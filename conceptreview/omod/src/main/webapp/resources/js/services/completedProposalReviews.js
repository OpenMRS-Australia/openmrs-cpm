define([
    'angular',
    'config',
    './index'
  ],
  function(services, config) {

    'use strict';

    angular.module('conceptreview.services').factory('CompletedProposalReviews',
      function($resource) {
        return $resource(
          config.contextPath + '/ws/conceptreview/completedProposalReviews/:proposalId',
          {proposalId: '@id'},
          {update: {method: 'PUT'}});
      }
    );
  }
);