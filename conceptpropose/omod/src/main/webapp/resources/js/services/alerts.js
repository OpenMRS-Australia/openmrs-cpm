define([
    './index',
    'config'
  ],
  function(services) {

    'use strict';

    var alerts = [];

    services.service('Alerts', function() {
      this.queue = function(alert) {
        alerts.push(alert);
      };

      this.dequeue = function() {
        var alertsToReturn = alerts;
        alerts = [];
        return alertsToReturn;
      };
    });
  }
);