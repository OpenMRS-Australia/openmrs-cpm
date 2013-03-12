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


    it("should not fetch anything and set the mode to create and initialise the status to 'DRAFT' when not given a proposal id", function() {
      routeParams = {};
      controller('EditProposalCtrl', {$scope: scope, $routeParams: routeParams});
      try {
        httpBackend.flush();
      } catch (e) {}

      expect(scope.proposal.name).toBeUndefined();
      expect(scope.isEdit).toBe(false);
      expect(scope.proposal.status).toBe("DRAFT");
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

    it("should delete a proposal by DELETE-ing to the address of the resource", function() {
      routeParams = {proposalId: 1};
      httpBackend.expectGET('/openmrs/ws/cpm/proposals/1').respond({id: 1, name: "A single proposal", description: "foo", status: "DRAFT"});
      controller('EditProposalCtrl', {$scope: scope, $routeParams: routeParams});
      httpBackend.flush();
      
      httpBackend.expectDELETE('/openmrs/ws/cpm/proposals/1').respond({});
      scope.deleteProposal();
    });

    it("should submit a proposal by PUT-ing a proposal model with status: 'TBS'", function() {

      // initiate controller
      routeParams = {proposalId: 1};
      httpBackend.whenGET('/openmrs/ws/cpm/proposals/1').respond({id: 1, name: "existing", email: "blah@blah.com"});
      controller('EditProposalCtrl', {$scope: scope, $routeParams: routeParams});
      httpBackend.flush();

      // assert that a PUT is received, with the status of the model changed to "TBS"
      httpBackend.expectPUT('/openmrs/ws/cpm/proposals/1', {id: 1, name: "existing", email: "blah@blah.com", status: "TBS"}).respond({});

      // Test the actual Code Under Test
      scope.submit();
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
