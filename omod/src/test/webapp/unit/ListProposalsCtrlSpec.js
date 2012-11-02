define(['angular-mocks', 'ListProposalsCtrl'], function() {
  'use strict';

  describe("ListProposals Controller Spec", function() {

    var scope;
    var httpBackend;

    beforeEach(module('cpm'));

    beforeEach(inject(function($rootScope, $controller, $httpBackend) {
      scope = $rootScope.$new();
      httpBackend = $httpBackend;

      httpBackend.expectGET('/openmrs/ws/cpm/proposals').respond([{id: 1, description: "Test", status: "DRAFT"}]);
      var controller = $controller('ListProposalsCtrl', {$scope: scope});
    }));


    it("should fetch a list of proposals (packages) and display them", function() {
      httpBackend.flush();
      expect(scope.proposals.length).toBe(1);
      expect(scope.proposals[0].description).toBe('Test');
    });

  });
});
