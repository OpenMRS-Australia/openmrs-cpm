define(['angular-mocks', 'js/controllers/ListProposalsCtrl'], function() {
  'use strict';

  describe('ListProposals Controller Spec', function() {

    var scope;
    var httpBackend;
    var controller;
    var menuService;

    beforeEach(module('conceptpropose.controllers'));

    beforeEach(inject(function($rootScope, $controller, $httpBackend, Menu) {
      scope = $rootScope.$new();
      controller = $controller;
      httpBackend = $httpBackend;
      menuService = Menu;
    }));

    beforeEach(function () {
      httpBackend.whenGET('/openmrs/ws/conceptpropose/proposals').respond([]);
    });

    it('should get menu', function () {
      var menuResponse = 'something';
      spyOn(menuService, 'getMenu').andCallFake(function (index) {
        expect(index).toBe(2);
        return menuResponse;
      });
      
      controller('ListProposalsCtrl', {$scope: scope, $routeParams: {}});

      expect(scope.menu).toBe(menuResponse);
    });

    it('should have reponseReceived initialised to false', function() {
      controller('ListProposalsCtrl', {$scope: scope});
      // no flush

      expect(scope.responseReceived).toBe(false);
    });


    it('should fetch a list of proposals (packages) and display them', function() {
      httpBackend.expectGET('/openmrs/ws/conceptpropose/proposals').respond([{id: 1, description: 'Test', status: 'DRAFT'}]);
      controller('ListProposalsCtrl', {$scope: scope});
      httpBackend.flush();

      expect(scope.responseReceived).toBe(true);
      expect(scope.proposals.length).toBe(1);
      expect(scope.proposals[0].description).toBe('Test');
    });

  });
});