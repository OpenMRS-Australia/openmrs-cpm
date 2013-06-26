define(['./index'], function(filters) {

  filters.filter('boolToYesOrNo', function() {
    return function(input) {
      return input ? "Yes" : "No";
    };
  });
});