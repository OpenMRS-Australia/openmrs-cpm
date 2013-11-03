define([
    'angular',
    'config',
    './index'
  ],
  function(angular, config) {

    'use strict';

    angular.module('cpm.services').factory('Settings',
      function($resource) {
        return $resource(config.contextPath + '/ws/cpm/settings');
      }
    );
  }
);