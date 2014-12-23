define([
    'angular',
    'config',
    './index'
  ],
  function(services, config) {

    'use strict';

    angular.module('conceptreview.services').factory('DeletedProposalReviews',
      function($resource) {
        return $resource(
          config.contextPath + '/ws/conceptreview/deletedProposalReviews/:proposalId',
          {proposalId: '@id'},
          {update: {method: 'PUT'}});
      }
    );
  }
);