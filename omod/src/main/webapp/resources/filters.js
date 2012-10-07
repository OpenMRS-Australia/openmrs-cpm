define(['cpm'], function(cpm) {
  cpm.filter('proposalStatus', function() {
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
});
