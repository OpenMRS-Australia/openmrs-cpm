cpm.controller('SearchConceptsDialogCtrl', ['$scope', '$http', function SearchConceptsDialogCtrl($scope, $http) {

  var SEARCH_DELAY = 250;

  var IGNORE_KEYCODES = [9,12,13,16,17,18,19,20,32,33,34,35,36,37,38,39,40,45,91,92,93,112,113,114,115,116,117,118,119,120,121,122,123,144,145,224];

  var RETURN = 13;

  var currTimeout;

  function doSearch() {

    $http.get('concepts.json').success(function(data) {
      var concept_list = data;
      $scope.$apply($scope.concepts = []);
      var query = $scope.query;
      for (var i in concept_list) {
        var currRow = concept_list[i];
        if (currRow.name.toLowerCase().indexOf(query.toLowerCase()) != -1 || currRow.description.toLowerCase().indexOf(query.toLowerCase()) != -1) {
          $scope.$apply(function(){
            $scope.concepts.push(concept_list[i]);
          });
        }
      }
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
    if (!found) {
      if (typeof currTimeout != "undefined") {
        clearTimeout(currTimeout)
      }
      currTimeout = setTimeout(function() {
        doSearch();
      }, SEARCH_DELAY);

    // trigger search immediately
    } else if (which == RETURN) {
      if (typeof currTimeout != "undefined") {
        clearTimeout(currTimeout)
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
