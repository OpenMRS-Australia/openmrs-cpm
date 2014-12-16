define(['angular-mocks', 'js/controllers/SettingsCtrl'], function() {
  'use strict';

  describe('SettingsCtrl', function() {

    var scope;
    var httpBackend;
    var controller;
    var menuService;

    beforeEach(function () {
      module('conceptpropose.controllers');
      inject(function($rootScope, $controller, $httpBackend, Menu) {
        scope = $rootScope.$new();
        controller = $controller;
        httpBackend = $httpBackend;
        menuService = Menu;
      });
      httpBackend.whenGET('/openmrs/ws/conceptpropose/settings').respond({});
    });

    it('should get menu', function () {
      var menuResponse = 'something';
      spyOn(menuService, 'getMenu').andCallFake(function (index) {
        expect(index).toBe(3);
        return menuResponse;
      });
      
      controller('SettingsCtrl', {$scope: scope, $routeParams: {}});

      expect(scope.menu).toBe(menuResponse);
    });

    describe('testConnection', function () {

      it('should not show message before request completes', function () {
        httpBackend.expectPOST('/openmrs/ws/conceptpropose/settings/connectionResult').respond(200, 'Success');

        controller('SettingsCtrl', {$scope: scope, $routeParams: {}});
        scope.testConnection();

        expect(scope.connectErrorMessage).toEqual('');
      });

      it('should show message when test succeded', function () {
        httpBackend.whenPOST('/openmrs/ws/conceptpropose/settings/connectionResult').respond(200, 'Success');

        controller('SettingsCtrl', {$scope: scope, $routeParams: {}});
        scope.testConnection();
        httpBackend.flush();

        expect(scope.settingsValid).toEqual(true);
        expect(scope.connectErrorMessage).toEqual('Success');
        expect(scope.isLoading).toEqual(false);
      });

      it('should show error when error returned', function () {
        httpBackend.whenPOST('/openmrs/ws/conceptpropose/settings/connectionResult').respond(200, 'error');

        controller('SettingsCtrl', {$scope: scope, $routeParams: {}});
        scope.testConnection();
        httpBackend.flush();

        expect(scope.settingsValid).toEqual(false);
        expect(scope.connectErrorMessage).toEqual('error');
        expect(scope.isLoading).toEqual(false);
      });

      it('should show Unknown Error when empty error returned', function () {
        httpBackend.whenPOST('/openmrs/ws/conceptpropose/settings/connectionResult').respond(500, {});

        controller('SettingsCtrl', {$scope: scope, $routeParams: {}});
        scope.testConnection();
        httpBackend.flush();

        expect(scope.settingsValid).toEqual(false);
        expect(scope.connectErrorMessage).toEqual('Unknown Error');
        expect(scope.isLoading).toEqual(false);
      });

    });

  });
});