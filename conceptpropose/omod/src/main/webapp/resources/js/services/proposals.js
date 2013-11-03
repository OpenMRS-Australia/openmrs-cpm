define([
    'angular',
    'config',
    './index'
  ],
  function(angular, config) {

    'use strict';

    angular.module('cpm.services').factory('Proposals',
      function($resource) {
        return $resource(
         config.contextPath + '/ws/cpm/proposals/:proposalId',
          { proposalId:'@id' },
          { update: {method: 'PUT'}, cache: true }
        );
      }
    );
  }
);