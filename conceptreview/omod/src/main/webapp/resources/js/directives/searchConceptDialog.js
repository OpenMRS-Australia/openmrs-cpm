define(['./index', 'config', 'js/controllers/SearchConceptDialogCtrl', './jqueryUiDialog'],
  function(directives, config) {
    'use strict';
    directives.directive('searchConceptDialog', function() {
      return {
        restrict: 'E',
        replace: true,
        scope: {
          isMultiple: '=isMultiple',
          onAcceptFn: '=onAcceptFn'
        },
        require: '^jqueryUiDialog',
        templateUrl: config.resourceLocation + '/partials/SearchConceptDialog.html',
        controller: 'SearchConceptDialogCtrl',
        link: function($scope, ele, attr, jqueryUiDialog) {
          $scope.cancel = function() {
            jqueryUiDialog.closeDialog();
          };
        }
      };
    });
  });