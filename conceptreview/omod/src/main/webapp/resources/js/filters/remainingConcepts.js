define(['./index'],
  function(filters) {

    'use strict';

    filters.filter('remainingConcepts', function() {
      return function(input) {
        return _.filter(input, function(i){ return i.status === 'RECEIVED'; }).length;
      };
    });
  }
);