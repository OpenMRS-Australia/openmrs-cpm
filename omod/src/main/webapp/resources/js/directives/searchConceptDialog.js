define(['./index', 'config', 'js/controllers/SearchConceptDialogCtrl', './jqueryUiDialog'], 
function(directives, config) {
    directives.directive('searchConceptDialog', function() {
        return {
            restrict: 'E',
            replace: true,
            scope: {
                isOpen: "=dialogOpen",
                isMultiple: "=isMultiple",
                onAcceptFn: "=onAcceptFn"
            },
            require: 'jqueryUiDialog',
            templateUrl: config.resourceLocation + "/partials/SearchConceptDialog.html",
            controller: 'SearchConceptDialogCtrl',
            link: function($scope, element, attr, jqueryUiDialog) {
                console.log($scope);
                $scope.$watch('isOpen', function(isOpen) {
                    jqueryUiDialog.dialog(isOpen);
                });
            }
        };
    });
});