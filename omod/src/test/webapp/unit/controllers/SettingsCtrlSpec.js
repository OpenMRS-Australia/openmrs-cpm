define(['angular-mocks', 'js/controllers/SettingsCtrl'], function() {
  'use strict';

  describe("SettingsCtrl", function() {

    var scope;
    var httpBackend;
    var controller;
    var menuService;

    beforeEach(function () {
      module('cpm.controllers');
      inject(function($rootScope, $controller, $httpBackend, Menu) {
        scope = $rootScope.$new();
        controller = $controller;
        httpBackend = $httpBackend;
        menuService = Menu;
      });
      httpBackend.whenGET('/openmrs/ws/cpm/settings').respond({});
    });

    it('should get menu', function () {
      var menuResponse = 'something';
      spyOn(menuService, 'getMenu').andCallFake(function (index) {
        expect(index).toBe(4);
        return menuResponse;
      });
      
      controller('SettingsCtrl', {$scope: scope, $routeParams: {}});

      expect(scope.menu).toBe(menuResponse);
    });

  });
});