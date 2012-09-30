var cpm = angular.module('cpm', []);

cpm.config(function($routeProvider){
  $routeProvider.when('/', {controller: CreateConceptProposalCtrl, templateUrl: 'CreateConceptProposal.html'});
  $routeProvider.when('/search-concepts', {controller: SearchConceptsDialogCtrl, templateUrl: 'SearchConceptsDialog.html'});
});


cpm.directive('cpmKeyup', function() {
  return function(scope, el, attrs) {
    var keyupFn = scope.$eval(attrs.cpmKeyup);
    el.bind("keyup", function(e) {
      scope.$apply(function() {
        keyupFn.call(scope, e.which);
      });
    });
  };
});

cpm.directive('jqueryUiDialog', function () {
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
