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
        scope: {
          title: '=title',
          dialogOpen: '=dialogOpen'
        },
        controller: function ($scope) {
          $scope.$watch('dialogOpen', function (isOpen) {
            var open = isOpen ? 'open' : 'close';
            $scope.$element.dialog(open);
          });
        },
        link: function ($scope, element) {
          $scope.dialogOpen = false;
          $scope.$element = $(element);

          $scope.$element.dialog({
            autoOpen: false,
            width: 800,
            title: $scope.title,
            close: function () {
              if ($scope.dialogOpen) {
                $scope.$apply(function () {
                  $scope.dialogOpen = false;
                });
              }
            }
          });
        }
      };
    });
  });