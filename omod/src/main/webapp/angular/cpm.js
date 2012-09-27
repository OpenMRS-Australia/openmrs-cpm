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
