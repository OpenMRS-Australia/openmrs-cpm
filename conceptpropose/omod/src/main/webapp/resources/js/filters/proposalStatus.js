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

        case 'DELETED':
          return 'Deleted';

        case 'DOESNOTEXIST':
          return 'Does not exist';

        default:
          return '';
        }
      };
    });
  }
);