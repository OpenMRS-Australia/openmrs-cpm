define(['./index'],
  function(filters) {

    'use strict';

    filters.filter('boolToYesOrNo', function() {
      return function(input) {
        return input ? 'Yes' : 'No';
      };
    });
  }
);