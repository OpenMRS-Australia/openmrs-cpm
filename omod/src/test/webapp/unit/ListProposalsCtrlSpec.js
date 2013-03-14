define(['angular-mocks', 'ListProposalsCtrl'], function() {
  'use strict';

  describe("ListProposals Controller Spec", function() {

    var scope;
    var httpBackend;
    var controller;

    beforeEach(module('cpm'));

    beforeEach(inject(function($rootScope, $controller, $httpBackend) {
      scope = $rootScope.$new();
      controller = $controller;
      httpBackend = $httpBackend;
    }));

    it("should have reponseReceived initialised to false", function() {
      httpBackend.expectGET('/openmrs/ws/cpm/proposals').respond([]);
      controller('ListProposalsCtrl', {$scope: scope});
      // no flush

      expect(scope.responseReceived).toBe(false);
    });


    it("should fetch a list of proposals (packages) and display them", function() {
      httpBackend.expectGET('/openmrs/ws/cpm/proposals').respond([{id: 1, description: "Test", status: "DRAFT"}]);
      controller('ListProposalsCtrl', {$scope: scope});
      httpBackend.flush();

      expect(scope.responseReceived).toBe(true);
      expect(scope.proposals.length).toBe(1);
      expect(scope.proposals[0].description).toBe('Test');
    });

    it("should fetch a list of filtered proposals (packages) and display them", function() {
        httpBackend.expectGET('/openmrs/ws/cpm/proposals/status/draft').respond([{id: 1, description: "Test", status: "DRAFT"}]);
        var filterParams = {status: 'draft'};
      controller('ListProposalsByStatusCtrl', {$scope: scope, $status: filterParams});
      httpBackend.flush();

      expect(scope.responseReceived).toBe(true);
      expect(scope.proposals.length).toBe(1);
      expect(scope.proposals[0].description).toBe('Test');
    });

  });
});
