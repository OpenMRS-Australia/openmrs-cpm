define([
    'angular',
    'js/controllers/SearchConceptsDialogCtrlFactory',
    './index'
  ],
  function(angular, SearchConceptsDialogCtrlFactory) {

    'use strict';
    
    angular.module('cpm.controllers').controller(
      'SearchConceptsDialogCtrl',
      SearchConceptsDialogCtrlFactory);
  }
);