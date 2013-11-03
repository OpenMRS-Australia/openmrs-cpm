define(['angular-mocks', 'js/controllers/ReviewProposalCtrl'], function() {
  'use strict';

  describe("ReviewProposalCtrl Controller Spec", function() {

    var scope;
    var httpBackend;
    var controller;
    var menuService;

    beforeEach(function () {
      module('cpmr.controllers')
      inject(function($rootScope, $controller, $httpBackend, Menu) {
        scope = $rootScope.$new();
        controller = $controller;
        httpBackend = $httpBackend;
        menuService = Menu;
      });
      httpBackend.whenGET('/openmrs/ws/cpm/proposalReviews').respond({});
    });

    it('should get menu', function () {
      var menuResponse = 'something';
      spyOn(menuService, 'getMenu').andCallFake(function (index) {
        expect(index).not.toBeDefined();
        return menuResponse;
      });
      
      controller('ReviewProposalCtrl', {$scope: scope, $routeParams: {}});

      expect(scope.menu).toBe(menuResponse);
    });

  });
});