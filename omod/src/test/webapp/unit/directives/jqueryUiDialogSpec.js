define([
  'angular-mocks',
  'js/directives/jqueryUiDialog'
],
        function() {

          'use strict';

          describe('jqueryUiDialog directive spec', function() {

            var $scope, element, dialog;
            var title = "test title";
            beforeEach(module('cpm.directives'));

            beforeEach(function() {
              inject(function($rootScope, $compile) {
                $scope = $rootScope.$new();

                $scope.open = false;

                element = $compile('<jquery-ui-dialog title=\'' + title + '\' dialog-open=\'open\'></jquery-ui-dialog>')($scope);

                $scope.$apply();

                dialog = element.scope().$element;

              });
            });

            it('Title should be equal "test title"', function() {
              expect(dialog.dialog('option', 'title')).toBe(title);
            });

            it('should open dialog', function() {
              expect(dialog.dialog("isOpen")).toBe(false);

              $scope.$apply($scope.open = true);
              expect(dialog.dialog("isOpen")).toBe(true);
            });
          });
        }
);