define(['angular-mocks', 'EditProposalCtrl'], function() {
  'use strict';

  describe("Edit Proposal Controller Spec", function() {

    var scope;
    var httpBackend;
    var routeParams;
    var controller;

    beforeEach(module('cpm'));

    beforeEach(inject(function($rootScope, $controller, $httpBackend) {
      scope = $rootScope.$new();
      httpBackend = $httpBackend;
      controller = $controller;
    }));


    it("should not fetch anything and set the mode to create when not given a proposal id", function() {
      routeParams = {};
      controller('EditProposalCtrl', {$scope: scope, $routeParams: routeParams});
      try {
        httpBackend.flush();
      } catch (e) {}

      expect(scope.proposal.name).toBeUndefined();
      expect(scope.isEdit).toBe(false);
    });

    it("should fetch a proposal and set the mode to edit given a proposal id", function() {
      routeParams = {proposalId: 1};
      httpBackend.expectGET('/openmrs/ws/cpm/proposals/1').respond({id: 1, name: "A single proposal", description: "foo", status: "DRAFT"});
      controller('EditProposalCtrl', {$scope: scope, $routeParams: routeParams});
      httpBackend.flush();

      expect(scope.proposal.name).toBe('A single proposal');
      expect(scope.proposal.description).toBe('foo');
      expect(scope.isEdit).toBe(true);
    });

    it("should save a new proposal by POST-ing to the list of proposals", function() {
      routeParams = {};
      controller('EditProposalCtrl', {$scope: scope, $routeParams: routeParams});

      scope.name = "new";
      scope.email = "blah@blah.com";
      scope.description = "proposal";

      httpBackend.expectPOST('/openmrs/ws/cpm/proposals').respond({id: 1, name: "new", email: "blah@blah.com", description: "proposal"});
      scope.save();
    });

    it("should save an existing proposal by PUT-ing to the address of the resource", function() {
      routeParams = {proposalId: 1};
      httpBackend.expectGET('/openmrs/ws/cpm/proposals/1').respond({id: 1, name: "A single proposal", description: "foo", status: "DRAFT"});
      controller('EditProposalCtrl', {$scope: scope, $routeParams: routeParams});
      httpBackend.flush();

      httpBackend.expectPUT('/openmrs/ws/cpm/proposals/1').respond({id: 1, name: "new", email: "blah@blah.com", description: "proposal"});
      scope.save();
    });

    /*
     * Not sure how to bind to view to get access to form validation yet
     *
    it("should not show any error messages for a clean form", function() {
      routeParams = {};
      controller('EditProposalCtrl', {$scope: scope, $routeParams: routeParams});

      expect(scope.nameErrorMsg()).toBe("");
      expect(scope.emailErrorMsg()).toBe("");
    });
     */


    afterEach(function(){
      httpBackend.verifyNoOutstandingExpectation();
    });

  });
});
