define(['angular-mocks', 'js/controllers/SearchConceptsDialogCtrl'], function() {
    'use strict';
    describe("Search Concepts Dialog Spec", function() {

        var scope, httpBackend, controller, timeout;
        
	var getUrl = function(qry, reqNum) {
            return '/openmrs/ws/cpm/concepts?query=' + qry + '&requestNum=' + reqNum;
	}

	var doSearch = function(scope, timeout, http, qry) {
	    scope.query = qry;
            scope.search(qry);
            timeout.flush();
            httpBackend.flush();
	}

        beforeEach(module('cpm.controllers'));

        beforeEach(inject(function($rootScope, $controller, $httpBackend, $timeout) {
            scope = $rootScope.$new();
            controller = $controller;
            httpBackend = $httpBackend;
            timeout = $timeout;
            controller('SearchConceptsDialogCtrl', {$scope: scope, $timeout: timeout});
        }));

        it("should return concept search results", function() {
            var qry1 = 'a';
	    var conceptMock1 = [1, 2, 3];
            var qry2 = 'ab';
	    var conceptMock2 = [4, 5, 6];

	    var reqUrl1 = getUrl(qry1, 1);
	    var reqUrl2 = getUrl(qry2, 2);
	    
            httpBackend.whenGET(reqUrl1).respond(200, { requestNum: 1, concepts: conceptMock1 });
            httpBackend.whenGET(reqUrl2).respond(200, { requestNum: 2, concepts: conceptMock2 });
            //kick off a search 
	    doSearch(scope, timeout, httpBackend, qry1);
            //check that current request number is incremented
	    expect(scope.currentRequestNum).toEqual(1); 
	    //check that results are expected
	    expect(scope.concepts).toEqual(conceptMock1);
	    
	    doSearch(scope, timeout, httpBackend, qry2);
            expect(scope.currentRequestNum).toEqual(2); 
	    expect(scope.concepts).toEqual(conceptMock2);
        });

	it("should throw away slow results that may override current/timely results", 
	function() {
            var qry1 = 'aba';
	    var conceptMock = [1, 2, 3];
	    var reqUrl1 = getUrl(qry1, 1);
            httpBackend.whenGET(reqUrl1).respond(200, 
		{ requestNum: 1, concepts: conceptMock });
	    scope.query = qry1;
            scope.search(qry1);
            timeout.flush();
	    scope.currentRequestNum = 3;//incr current req counter before executing mock request
            httpBackend.flush();
	    expect(scope.currentRequestNum).toEqual(3);
	    //check that results are thrown away
	    expect(scope.concepts).not.toEqual(conceptMock);
	});

        afterEach(function(){
            httpBackend.verifyNoOutstandingExpectation();
            httpBackend.verifyNoOutstandingRequest();
        });
    });
});
