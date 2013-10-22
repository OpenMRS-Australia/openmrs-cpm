define(['angular-mocks', 'js/controllers/ListIncomingProposalsCtrl'], function() {
  'use strict';

  describe("ListIncomingProposals Controller Spec", function() {

    var scope;
    var httpBackend;
    var controller;
    var menuService;

    beforeEach(module('cpm.controllers'));

    beforeEach(inject(function($rootScope, $controller, $httpBackend, Menu) {
      scope = $rootScope.$new();
      controller = $controller;
      httpBackend = $httpBackend;
      menuService = Menu;
    }));

    it('should get menu', function () {
      httpBackend.expectGET('/openmrs/ws/cpm/proposalReviews').respond({});
      var menuResponse = 'something';
      spyOn(menuService, 'getMenu').andCallFake(function (index) {
        expect(index).toBe(3);
        return menuResponse;
      });
      
      controller('ListIncomingProposalsCtrl', {$scope: scope, $routeParams: {}});

      expect(scope.menu).toBe(menuResponse);
    });

  });
});