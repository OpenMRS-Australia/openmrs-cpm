define([
  'angular-mocks',
  'lib/underscore',
  'js/controllers/EditProposalCtrl'
], function() {

  'use strict';

  describe('Edit Proposal Controller Spec', function() {

    var scope;
    var httpBackend;
    var routeParams;
    var controller;
    var alertsService;

    beforeEach(module('cpm.controllers'));

    beforeEach(inject(function($rootScope, $controller, $httpBackend, Alerts) {
      scope = $rootScope.$new();
      httpBackend = $httpBackend;
      controller = $controller;
      alertsService = Alerts;
    }));


    it('should not fetch anything and set the mode to create and initialise the status to \'DRAFT\' when not given a proposal id', function() {
      routeParams = {};
      controller('EditProposalCtrl', {$scope: scope, $routeParams: routeParams});
      try {
        httpBackend.flush();
      } catch (e) {}

      expect(scope.proposal.name).toBeUndefined();
      expect(scope.isEdit).toBe(false);
      expect(scope.proposal.status).toBe('DRAFT');
    });

    it('should fetch a proposal and set the mode to edit given a proposal id', function() {
      routeParams = {proposalId: 1};
      httpBackend
        .expectGET('/openmrs/ws/cpm/proposals/1')
        .respond({
          id: 1,
          name: 'A single proposal',
          description: 'foo',
          status: 'DRAFT'
        });
      controller('EditProposalCtrl', {$scope: scope, $routeParams: routeParams});
      httpBackend.flush();

      expect(scope.proposal.name).toBe('A single proposal');
      expect(scope.proposal.description).toBe('foo');
      expect(scope.isEdit).toBe(true);
    });

    it("shoud not allow users add same concept twice to proposal", function() {
      var testData = [{id:1}, {id:2}, {id:3}];
      var existingConcepts = [{id:1}, {id:2}, {id:4}];
      var expectedResult = [{id:1}, {id:2}, {id:3}, {id:4}];

      routeParams = {proposalId: 1};
      httpBackend.expectGET('/openmrs/ws/cpm/proposals/1').respond({id: 1, name: "A single proposal", description: "foo", status: "DRAFT"});
      controller('EditProposalCtrl', {$scope: scope, $routeParams: routeParams});
      httpBackend.flush();

      scope.proposal.concepts = existingConcepts;

      scope.proposal.concepts = scope.getConceptUnion(testData, existingConcepts);

      for (var x in expectedResult) {
        var inConcepts = scope.proposal.concepts.filter(function(e) {
          return e.id == expectedResult[x].id;
        });
          
        expect(inConcepts.length).toBe(1);
      }
    });

    it(
      'should save a new proposal by POST-ing to the list of proposals and redirecting to proposal list', 
      inject(function($location) {
        routeParams = {};
        controller('EditProposalCtrl', {$scope: scope, $routeParams: routeParams});

        scope.name = 'new';
        scope.email = 'blah@blah.com';
        scope.description = 'proposal';

        httpBackend
          .expectPOST('/openmrs/ws/cpm/proposals')
          .respond({
            id: 1,
            name: 'new',
            email: 'blah@blah.com',
            description: 'proposal'
          });

        scope.save();

        httpBackend.flush();        

        expect($location.path()).toBe('/');
      }
    ));

    it('should pass a success route parameter after saving', inject(function(Alerts) {
      
      routeParams = { proposalId: 1 };
      httpBackend
        .expectGET('/openmrs/ws/cpm/proposals/1')
        .respond({
          id: 1,
          name: 'A single proposal',
          description: 'foo',
          status: 'DRAFT'
        });
      controller('EditProposalCtrl', {$scope: scope, $routeParams: routeParams});
      httpBackend.flush();

      httpBackend
        .expectPUT('/openmrs/ws/cpm/proposals/1')
        .respond({
          id: 1,
          name: 'new',
          email: 'blah@blah.com',
          description: 'proposal'
        });
      scope.save();

      httpBackend.flush();

      var alerts = Alerts.dequeue();

      expect(alerts.length).toBe(1);
    }));

    it('should delete a proposal by DELETE-ing to the address of the resource', function() {
      spyOn(window, 'confirm').andReturn(true);
      routeParams = {proposalId: 1};
      httpBackend
        .expectGET('/openmrs/ws/cpm/proposals/1')
        .respond({
          id: 1,
          name: 'A single proposal',
          description: 'foo',
          status: 'DRAFT'
        });
      controller('EditProposalCtrl', {$scope: scope, $routeParams: routeParams});
      httpBackend.flush();
      
      httpBackend.expectDELETE('/openmrs/ws/cpm/proposals/1').respond({});
      scope.deleteProposal();
    });

    it('should submit a proposal by PUT-ing a proposal model with status: \'TBS\'', function() {

      // initiate controller
      routeParams = {proposalId: 1};
      httpBackend
        .whenGET('/openmrs/ws/cpm/proposals/1')
        .respond({
          id: 1,
          name: 'existing',
          email: 'blah@blah.com',
          concepts:[{'name':'flu'}]
        });
      controller('EditProposalCtrl', {$scope: scope, $routeParams: routeParams});
      httpBackend.flush();

      var proposal = {
        id: 1,
        name: 'existing',
        email: 'blah@blah.com',
        concepts:[{'name':'flu'}],
        status: 'TBS'};
      httpBackend
        .expectPUT('/openmrs/ws/cpm/proposals/1', proposal)
        .respond({});

      scope.submit();
    });

    it('should be read-only if the status is anything other than \'DRAFT\'', function() {

        // initiate controller
        routeParams = {proposalId: 1};
        httpBackend
          .whenGET('/openmrs/ws/cpm/proposals/1').
          respond({
            id: 1,
            name: 'existing',
            email: 'blah@blah.com',
            status: 'SUBMITTED',
            concepts:[{'name':'flu'}]
          });
        controller('EditProposalCtrl', {$scope: scope, $routeParams: routeParams});
        httpBackend.flush();

        expect(scope.isReadOnly).toBe(true);
    });

    it('should be editable if the status is \'DRAFT\'', function() {

        // initiate controller
        routeParams = {proposalId: 1};
        httpBackend
          .whenGET('/openmrs/ws/cpm/proposals/1')
          .respond({
            id: 1,
            name: 'existing',
            email: 'blah@blah.com',
            status: 'DRAFT',
            concepts:[{'name':'flu'}]
          });
        controller('EditProposalCtrl', {$scope: scope, $routeParams: routeParams});
        httpBackend.flush();

        expect(scope.isReadOnly).toBe(false);
    });

    it('should be editable if we are creating a new proposal', function() {

        // initiate controller
        routeParams = {};
        controller('EditProposalCtrl', {$scope: scope, $routeParams: routeParams});

        expect(scope.isReadOnly).toBe(false);
    });

    it('should POST a new resource with status "TBS" if the user clicks "Send proposal" from the "Create Proposal" screen', function(){

        routeParams = {};
        controller('EditProposalCtrl', {$scope: scope, $routeParams: routeParams});

        scope.proposal.name = 'new';
        scope.proposal.email = 'blah@blah.com';
        scope.proposal.description = 'proposal';

        httpBackend.expectPOST('/openmrs/ws/cpm/proposals', {
          status: "TBS",
          concepts: [],
          name: "new",
          email: "blah@blah.com",
          description: "proposal"
        }).respond({});

        scope.submit();
    });

    afterEach(function(){
      httpBackend.verifyNoOutstandingExpectation();
      alertsService.dequeue();
    });

  });
});