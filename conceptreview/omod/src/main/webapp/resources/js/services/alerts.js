define([
    'angular',
    'config',
    './index'
  ],
  function(angular) {

    'use strict';

    var alerts = [];

    angular.module('cpmr.services').service('Alerts',
      function() {
        this.queue = function(alert) {
          alerts.push(alert);
        };

        this.dequeue = function() {
          var alertsToReturn = alerts;
          alerts = [];
          return alertsToReturn;
        };
      }
    );
  }
);