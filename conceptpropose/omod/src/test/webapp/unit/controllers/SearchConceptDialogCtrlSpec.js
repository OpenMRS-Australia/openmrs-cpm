define([
  'angular-mocks',
  'underscore',
  'js/controllers/SearchConceptDialogCtrl',
  'js/services/searchConcept'
], function() {

  'use strict';

  describe('Search Concept Dialog Spec', function() {

    var scope, controller, timeout, searchConcept, httpBackend;

    beforeEach(module('conceptpropose.controllers'));

    beforeEach(inject(function($rootScope, $controller, $timeout, SearchConcept, $httpBackend) {

      scope = $rootScope.$new();
      controller = $controller;
      timeout = $timeout;
      searchConcept = SearchConcept;
      httpBackend = $httpBackend;

      // Simulate backend response for blank search so timeout.flush() does not throw error
      httpBackend.whenGET(/\/openmrs\/ws\/conceptpropose\/concepts\?query=&requestNum=.*/).respond('whatever');

      controller('SearchConceptDialogCtrl', { $scope: scope,
        $timeout: timeout, SearchConcept: searchConcept});
    }));

    it('(#33) should not search if the search box is empty.',
        function() {
      // Initiate search with data
      scope.searchTerm = 'A';
      scope.search();

      // Clear search box and search again
      scope.searchTerm = '';
      scope.search();

      // Should be no timeout set for search
      expect(timeout.flush).toThrow();
//    timeout.verifyNoPendingTasks(); // available from version 1.1.2 of angular
    });
      
    it('(#33) should clear existing concept search results if the search box is empty.',
        function() {
      // Populate "existing concept search results"
      var mockData1 = [4, 5, 6];
      scope.currentRequestNum = 3;
      scope.concepts = mockData1;

      // Clear search box and search again
      scope.searchTerm = '';
      scope.search();

      // Check there are now no results
      expect(scope.concepts).toEqual([]);
    });

    it('should throw away slow results that may override current/timely results',
        function() {
      var mockData1 = [4, 5, 6];
      scope.currentRequestNum = 3;

      scope.concepts = mockData1;

      var mockData2 = { data : { requestNum: 1, concepts :
        [{names: [{name:1},{name:2},{name:3}],
          preferredName: 1}]} };

      scope.processSearchResults(mockData2);
      //incr current req counter before executing mock request
      expect(scope.currentRequestNum).toEqual(3);
      //check that results are thrown away
      expect(scope.concepts).toEqual(mockData1);
      expect(scope.concepts).not.toEqual(mockData2.concepts);
    });

    it('should accept latest search results returned from the database',
        function() {
      var mockData1 = [4, 5, 6];

      scope.currentRequestNum = 1;

      scope.concepts = mockData1;

      var mockData2 = { data : { requestNum: 5, concepts :
        [{names: [{name:1},{name:2},{name:3}],
          preferredName: 1}]}};

      scope.processSearchResults(mockData2);
      //incr current req counter before executing mock request
      expect(scope.currentRequestNum).toEqual(5);
      //check that results are thrown away
      expect(scope.concepts).not.toEqual(mockData1);
      expect(scope.concepts).toEqual(mockData2.data.concepts);
    });

    it('should concatenate names of concepts into a list of synonyms, ' +
        'whilst excluding preferred name from the list of synonyms',
        function() {
      var mockData2 = { data :{ requestNum: 5, concepts : [{names:
        [{name:'1'},{name:'2'},{name:'3'}], preferredName: '1'}]}};

      scope.processSearchResults(mockData2);
      expect(scope.concepts[0].synonyms).toEqual('2, 3');
      expect(scope.concepts[0].preferredName).toEqual('1');
    });

    afterEach(function(){
    });
  });
});

