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

    return angular.module('cpm', [
      'cpm.services',
      'cpm.controllers',
      'cpm.filters',
      'cpm.directives'
    ]);
  }
);