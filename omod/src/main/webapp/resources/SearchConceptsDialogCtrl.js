define(['cpm'], function(cpm) {
  cpm.controller('SearchConceptsDialogCtrl', ['$scope', '$http', '$timeout', function SearchConceptsDialogCtrl($scope, $http, $timeout) {

    var SEARCH_DELAY = 250;

    var IGNORE_KEYCODES = [9,12,13,16,17,18,19,20,32,33,34,35,36,37,38,39,40,45,91,92,93,112,113,114,115,116,117,118,119,120,121,122,123,144,145,224];

    var RETURN = 13;

    var currTimeout;

    function doSearch() {
      $http.get('/openmrs/ws/rest/v1/concept?v=full&q=' + encodeURIComponent($scope.query)).success(function(data) {
        $scope.concepts = data.results;
      /*
        for (var i in data.results) {
          var result = data.results[i];
          var concept = {
            id: result.uuid,
            name: result.display,
            description: result.name.display
          };
          $scope.concepts.push(concept)
        }
        */
      });
    }

    $scope.search = function(which) {
      var found = false;
      for (var i in IGNORE_KEYCODES) {
        if (IGNORE_KEYCODES[i] == which) {
          found = true;
          break;
        }
      }

      // delay search
      if (!found) {
        if (typeof currTimeout != "undefined") {
          $timeout.cancel(currTimeout); 
        }
        currTimeout = $timeout(function() {
          doSearch();
        }, SEARCH_DELAY);

      // else if return key, trigger search immediately
      } else if (which == RETURN) {
        if (typeof currTimeout != "undefined") {
          $timeout.cancel(currTimeout);
        }
        doSearch();
      }
    };

    $scope.add = function() {
      var concepts = getSelectedConcepts();
      $scope.$parent.$parent.selectedConcepts = $scope.$parent.$parent.selectedConcepts.concat(concepts);
      $scope.$parent.$parent.dialog = "close";
    };

    $scope.cancel = function() {
      $scope.$parent.$parent.dialog = "close";
    };

    $scope.concepts = [];

    function getSelectedConcepts() {
      var selectedList = [];

      for (var i in $scope.concepts) {
        if ($scope.concepts[i].selected) {
          selectedList[selectedList.length] = $scope.concepts[i];
        }
      }

      return selectedList;
    }

  }]);
});
