define([
    './index',
    'jquery',
    'jquery-ui'
  ],
  function (directives, $) {

    'use strict';

    directives.directive('jqueryUiDialog', function () {
      return {
        restrict: 'E',
        replace: true,
        transclude: true,
        template: '<div><div ng-transclude></div></div>',
        scope: {
          title: '@title',
          dialogOpen: '=dialogOpen'
        },
        controller: function ($scope) {
          $scope.$watch('dialogOpen', function (isOpen) {
            var open = isOpen ? 'open' : 'close';
            $scope.$element.dialog(open);
          });
          
          this.closeDialog = function() {
            $scope.dialogOpen = false;
          };
        },
        link: function ($scope, element) {
          $scope.dialogOpen = false;
          $scope.$element = $(element);
          
          $scope.$watch('title', function(title) {
            if (!_.isUndefined(title)) {
              $scope.$element.dialog({
                autoOpen: false,
                width: 800,
                title: title,
                close: function () {
                  if ($scope.dialogOpen) {
                    $scope.$apply(function () {
                      $scope.dialogOpen = false;
                    });
                  }
                }
              });
            }
          });
        }
      };
    });
  });