define(['angular'], function(angular) {

  var filters = angular.module('cpm.filters', []);

  filters.filter('proposalStatus', function() {
    return function(input) {
      if (input === 'DRAFT') {
        return 'Draft';
      } else if (input === 'SUBMITTED') {
        return 'Submitted';
      } else {
        return '';
      }
    };
  });

  return filters;
});
