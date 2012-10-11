define(['angular', 'jquery', 'jquery-ui'], function(angular, $) {

  var directives = angular.module('cpm.directives', []);

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

  directives.directive('jqueryUiDialog', function () {
      return function (scope, element, attrs) {
          var dialog = $(element).dialog(
          {autoOpen:false, width: 800, title: "Add concepts",
          close:function()
              {
                  try
                  {
                  var functionName= attrs.jqueryUiDialogClosed;
                  //alert(functionName);
                  if(typeof scope[functionName]=="function")
                      setTimeout(function(){scope.$apply(scope[functionName]);},100);
                  }
                  catch(err)
                  {}
              }
          });
          scope.$watch("dialog", function (value) {
              $(element).dialog(value);
          });
      }
  });

  return directives;
});
