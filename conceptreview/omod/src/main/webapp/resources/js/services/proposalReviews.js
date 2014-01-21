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
          config.contextPath + '/ws/conceptreview/proposalReviews/:proposalId',
          {proposalId: '@id'},
          {update: {method: 'PUT'}});
      }
    );
  }
);