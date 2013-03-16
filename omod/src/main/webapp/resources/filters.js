define(['angular'], function(angular) {

  var filters = angular.module('cpm.filters', []);

  filters.filter('proposalStatus', function() {
    return function(input) {
      switch (input) {

        case 'DRAFT':
          return 'Draft';

        case 'SUBMITTED':
          return 'Submitted';

        default:
          return '';
      }
    };
  });

  filters.filter('proposalReviewStatus', function() {
    return function(input) {
      switch (input) {

        case 'RECEIVED':
          return 'Open';

        case 'CLOSED_NEW':
          return 'New';

        case 'CLOSED_EXISTING':
          return 'Existing';

        case 'CLOSED_REJECTED':
          return 'Rejected';

        default:
          return '';
      }
    }
  })

  return filters;
});
