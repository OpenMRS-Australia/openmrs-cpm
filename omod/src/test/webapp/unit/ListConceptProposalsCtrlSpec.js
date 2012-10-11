define(['angular-mocks', 'ListConceptProposalsCtrl'], function() {
  'use strict';

  describe("ListConceptProposals Controller Spec", function() {

    var scope;
    var httpBackend;

    beforeEach(module('cpm'));

    beforeEach(inject(function($rootScope, $controller, $httpBackend) {
      scope = $rootScope.$new();
      httpBackend = $httpBackend;
      httpBackend.whenGET('/openmrs/module/cpm/rest/proposals.list').respond([{id: 1, description: "Test", status: "DRAFT"}]);
      var controller = $controller('ListConceptProposalsCtrl', {$scope: scope});
    }));


    it("should fetch a list of concept proposals display them", function() {
      httpBackend.flush();
      expect(scope.proposals.length).toBe(1);
    });

  });
});
