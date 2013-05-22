define(['angular'], function(angular) {

  var directives;
  try {
      directives = angular.module('cpm.directives');
  }
  catch(err) {
    directives = angular.module("cpm.directives", []);
  }

	directives.directive('cpmKeyup', function() {
    	return function(scope, el, attrs) {
      		var keyupFn = scope.$eval(attrs.cpmKeyup);
      		el.bind("keyup", function(e) {
        		scope.$apply(function() {
          			keyupFn.call(scope, e.which);
        		});
      		});
    	};
  	});
});