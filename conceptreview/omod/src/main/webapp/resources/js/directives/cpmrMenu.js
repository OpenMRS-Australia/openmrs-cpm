define(['./index'],
  function(directives) {

    'use strict';

    directives.directive('cpmrMenu', function() {
      return {
        restrict: 'E',
        replace: true,
        scope: {
          menu: '='
        },
        template: '<ul id="menu">' +
                    '<li ng-repeat="m in menu" ng-class="{active: m.active, first: $first}">' +
                      '<a href="{{m.link}}">{{m.text}}</a>' +
                    '</li>' +
                  '</ul>'
      };
    });
  }
);