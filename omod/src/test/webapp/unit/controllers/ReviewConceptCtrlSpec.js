define(['angular-mocks', 'js/controllers/ReviewConceptCtrl'], function() {
  'use strict';

  describe("Review Concept Controller Spec", function() {

    var scope;
    var httpBackend;
    var controller;

    beforeEach(module('cpm.controllers'));

    beforeEach(inject(function($rootScope, $controller, $httpBackend) {
      scope = $rootScope.$new();
      httpBackend = $httpBackend;
      controller = $controller;
    }));


    it("should fetch a comment from a supplied concept review", function() {
      httpBackend.expectGET('/openmrs/ws/cpm/proposalReviews/1/concepts/1').respond({
        id: 1,
        reviewComment: 'some comment'
      });
      controller('ReviewConceptCtrl', {$scope: scope, $routeParams: {proposalId: 1, conceptId: 1}});
      httpBackend.flush();

      expect(scope.concept.reviewComment).toBe('some comment');
    });

    it("should persist a comment", function() {
      httpBackend.expectGET('/openmrs/ws/cpm/proposalReviews/1/concepts/1').respond({
        id: 1,
        reviewComment: 'some comment'
      });
      controller('ReviewConceptCtrl', {$scope: scope, $routeParams: {proposalId: 1, conceptId: 1}})
      httpBackend.flush();

      httpBackend.expectPUT('/openmrs/ws/cpm/proposalReviews/1/concepts/1', '{"id":1,"reviewComment":"A comment to persist"}').respond({});

      scope.concept.reviewComment = "A comment to persist";
      scope.saveReviewComment();

    });

    afterEach(function(){
      httpBackend.verifyNoOutstandingExpectation();
    });

  });
});