define(['./index'], function(directives) {

	directives.directive('cpmMenu', function() {
    	return {
            restrict: 'E',
            replace: true,
            scope: {
                menu: '='
            },
            link: function($scope, $element) {
               
            },
            template: '<ul id="menu">' + 
                         '<li ng-repeat="m in menu" ng-class="{active: m.active}">' + 
            				'<a href="{{m.link}}">{{m.text}}</a>' + 
                         '</li>' + 
                      '</ul>'
        };
	});
});