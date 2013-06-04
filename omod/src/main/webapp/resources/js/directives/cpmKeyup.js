define(['./index'], function(directives) {

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