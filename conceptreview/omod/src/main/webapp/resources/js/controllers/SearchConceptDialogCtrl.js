define([
    './index',
    'config',
    'js/services/searchConcept',
    'js/directives/jqueryUiDialog'
  ],

  function(controllers, config) {
    'use strict';
    controllers.controller('SearchConceptDialogCtrl',
      function($scope, $timeout, SearchConcept) {
        var SEARCH_DELAY = 250;
        var searchTimeout;

        $scope.currentRequestNum = 0;
        $scope.contextPath = config.contextPath;
        $scope.concepts = [];

        var getSelectedConcepts = function() {

          if ($scope.isMultiple) {
            return _.filter($scope.concepts, function(e) {
              return e.selected;
            });
          } else {
            return [$scope.selectedConcept];
          }
        };

        var mergeNames = function(names, preferredName) {

          return _.reduce(names, function(prev, curr) {
            var conceptName = curr.name;
            if (conceptName !== '' &&
              conceptName !== preferredName) {
              if (prev === '') {
                return conceptName;
              } else {
                return prev + ', ' + conceptName;
              }
            } else {
              return prev;
            }
          }, '');
        };

        $scope.processSearchResults = function(response) {
          $scope.isSearching = false;
          if ($scope.searchTerm !== '') {
            var data = response.data;

            if (parseInt(data.requestNum, 10) >= $scope.currentRequestNum) {
              $scope.currentRequestNum = data.requestNum;
              $scope.concepts = data.concepts;

              _.forEach($scope.concepts, function(concept) {
                if (concept.names) {
                  concept.synonyms = mergeNames(concept.names,
                    concept.preferredName);
                }
              });
            }
          }
        };

        $scope.search = function() {

          if (typeof searchTimeout !== 'undefined') {
            $timeout.cancel(searchTimeout);
          }

          if ($scope.searchTerm !== '') {
            searchTimeout = $timeout(function() {
              $scope.currentRequestNum++;
              $scope.isSearching = true;
              SearchConcept
                .runQuery($scope.searchTerm, $scope.currentRequestNum)
                .then($scope.processSearchResults);
            }, SEARCH_DELAY);
          } else {
            $scope.concepts = [];
          }
        };

        $scope.conceptClicked = function(concept) {

          if ($scope.isMultiple) {
            concept.selected = !concept.selected;
          } else {
            $scope.selectedConcept = concept;
          }
        };

        $scope.add = function() {
          var concepts = getSelectedConcepts();
          $scope.onAcceptFn(concepts);
        };
      });
  });