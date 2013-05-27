define(['./index', 'config'], function(filters, config) {

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
});