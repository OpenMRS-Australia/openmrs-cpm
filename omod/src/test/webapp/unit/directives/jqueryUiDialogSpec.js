define([
  'angular-mocks',
  'js/directives/jqueryUiDialog'
],
        function() {

          'use strict';

          describe('jqueryUiDialog directive spec', function() {

            var $scope, element;
            var title = "test title";
            beforeEach(module('cpm.directives'));

            beforeEach(function() {
              inject(function($rootScope, $compile) {
                $scope = $rootScope.$new();

                $scope.testTitle = title;
                $scope.isOpen = false;

                element = $compile('<jquery-ui-dialog title=\'testTitle\' dialog-open=\'isOpen\'></jquery-ui-dialog>')($scope);

                $scope.$digest();
              });
            });

            it('Title should be equal "test title"', function() {
              var dialog = element.scope().$element;
              expect(dialog.dialog('option', 'title')).toBe(title);
            });

            it('should open dialog', function() {
              var dialog = element.scope().$element;
              expect(dialog.dialog("isOpen")).toBe(false);

              $scope.isOpen = true;
              $scope.$digest();
              expect(dialog.dialog("isOpen")).toBe(true);
            });
          });
        }
);