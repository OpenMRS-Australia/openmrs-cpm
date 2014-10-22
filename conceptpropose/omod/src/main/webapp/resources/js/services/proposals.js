define([
    'angular',
    'config',
    './index'
  ],
  function(angular, config) {

    'use strict';

    angular.module('conceptpropose.services').factory('Proposals',
      function($resource) {
        return $resource(
         config.contextPath + '/ws/conceptpropose/proposals/:proposalId',
          { proposalId:'@id' },
          { update: {method: 'PUT'} }
        );
      }
    );
  }
);