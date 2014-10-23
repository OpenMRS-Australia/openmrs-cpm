define([
    './index',
    'config'
  ],
  function(filters) {

    'use strict';

    filters.filter('proposalStatus', function() {
      return function(input) {
        switch (input) {

        case 'DRAFT':
          return 'Draft';

        case 'SUBMITTED':
          return 'Submitted';

        case 'CLOSED':
          return 'Closed';

        default:
          return '';
        }
      };
    });
  }
);