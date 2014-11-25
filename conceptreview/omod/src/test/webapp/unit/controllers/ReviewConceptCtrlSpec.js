define(['angular-mocks', 'js/controllers/ReviewConceptCtrl'], function() {
  'use strict';

  describe('Review Concept Controller Spec', function() {

    var scope;
    var httpBackend;
    var controller;
    var menuService;

    beforeEach(module('conceptreview.controllers'));

    beforeEach(inject(function($rootScope, $controller, $httpBackend, Menu) {
      scope = $rootScope.$new();
      httpBackend = $httpBackend;
      controller = $controller;
      menuService = Menu;
      httpBackend.whenGET('/openmrs/ws/conceptreview/userDetails').respond({});
    }));

    it('should get menu', function () {
      httpBackend.whenGET('/openmrs/ws/conceptreview/proposalReviews/1/concepts/1').respond({});
      var menuResponse = 'something';
      spyOn(menuService, 'getMenu').andCallFake(function (index) {
        expect(index).not.toBeDefined();
        return menuResponse;
      });
      
      controller('ReviewConceptCtrl', {$scope: scope, $routeParams: {proposalId: 1, conceptId: 1}});

      expect(scope.menu).toBe(menuResponse);
    });

    it('should fetch a comment from a supplied concept review', function() {
      httpBackend.expectGET('/openmrs/ws/conceptreview/proposalReviews/1/concepts/1').respond({
        id: 1,
        reviewComment: 'some comment'
      });
      controller('ReviewConceptCtrl', {$scope: scope, $routeParams: {proposalId: 1, conceptId: 1}});
      httpBackend.flush();

      expect(scope.concept.reviewComment).toBe('some comment');
    });

    it('should persist a comment', function() {
      httpBackend.expectGET('/openmrs/ws/conceptreview/proposalReviews/1/concepts/1').respond({
        id: 1,
        reviewComment: 'some comment'
      });
      controller('ReviewConceptCtrl', {$scope: scope, $routeParams: {proposalId: 1, conceptId: 1}});
      httpBackend.flush();

      httpBackend
        .expectPUT(
          '/openmrs/ws/conceptreview/proposalReviews/1/concepts/1',
          '{\"id\":1,\"reviewComment\":\"A comment to persist\"}')
        .respond({});

      scope.concept.reviewComment = 'A comment to persist';
      scope.addComment();

    });

    afterEach(function(){
      httpBackend.verifyNoOutstandingExpectation();
    });

  });
});