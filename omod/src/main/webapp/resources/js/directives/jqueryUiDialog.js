define([
    './index',
    'jquery',
    'jquery-ui'
  ],
  function(directives, $) {
	
	'use strict';

    directives.directive('jqueryUiDialog', function() {
        return {
            scope: {
                onClose: "&onClose",
                title: "@title"
            },
            controller: function($scope) {
                this.dialog = function(isOpen) {
                    $scope.isOpen = isOpen;
                    var open = isOpen ? "open" : "close";
                    $scope.$element.dialog(open);
                };
            },
            link: function($scope, element, attrs) {
                $scope.isOpen = false;
                $scope.$element = $(element);

                $scope.$element.dialog({
                    autoOpen: false,
                    width: 800,
                    title: $scope.title,
                    close: function() {
                        if ($scope.isOpen) {
                            $scope.$apply(function() {
                                $scope.onClose();
                            });
                        }
                    }
                });
            }
        }
    });
});