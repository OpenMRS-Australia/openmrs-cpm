define(['angular', 'jquery', 'jquery-ui'], function(angular, $) {

	var directives;
	try {
	    directives = angular.module('cpm.directives');
	}
	catch(err) {
		directives = angular.module("cpm.directives", []);
	}

	directives.directive('jqueryUiDialog', function () {
  		return function (scope, element, attrs) {
          	var dialog = $(element).dialog(
          	{
	          	autoOpen: false, 
	          	width: 800, 
	          	title: attrs.title,
	          	close: function() {
	            	try {
		            	var functionName = attrs.jqueryUiDialogClosed;
	                	if (typeof scope[functionName] === "function") {
	                    	setTimeout(
	                      		function(){ scope.$apply(scope[functionName]); },
	                      		100);
	                  	}
                  	}
                  	catch(err) { }
	            }
	        });
	        scope.$watch("dialog", function (value) {
	            $(element).dialog(value);
	        });
	    }
	});
});