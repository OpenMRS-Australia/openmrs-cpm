define([
    'angular',
    'config',
    'require',
    'underscore',
    'js/controllers/index',
    'js/directives/index',
    'js/filters/index',
    'js/services/index'
  ],
  function(angular) {

    'use strict';

    return angular.module('conceptpropose', [
      'conceptpropose.services',
      'conceptpropose.controllers',
      'conceptpropose.filters',
      'conceptpropose.directives'
    ]);
  }
);