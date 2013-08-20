define([
    './index',
    'jquery',
    'jquery-ui'
  ],
  function(directives, $) {

    'use strict';

    directives.directive('jqueryUiDialog', function () {
      return function (scope, element, attrs) {
        $(element).dialog({
          autoOpen: false,
          width: 800,
          title: attrs.title,
          close: function() {
            try {
              var functionName = attrs.jqueryUiDialogClosed;
              if (typeof scope[functionName] === 'function') {
                setTimeout(
                  function(){ scope.$apply(scope[functionName]); },
                  100);
              }
            }
            catch(err) { }
          }
        });

        scope.$watch('dialog', function (value) {
          $(element).dialog(value);
        });
      };
    });
  }
);