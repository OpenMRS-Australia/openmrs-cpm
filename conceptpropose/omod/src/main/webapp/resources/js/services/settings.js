define([
    'angular',
    'config',
    './index'
  ],
  function(angular, config) {

    'use strict';

    angular.module('conceptpropose.services').factory('Settings',
      function($resource) {
        return $resource(config.contextPath + '/ws/conceptpropose/settings');
      }
    );
  }
);