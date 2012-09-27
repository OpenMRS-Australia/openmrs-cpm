function SearchConceptsDialogCtrl($scope) {

  var SEARCH_DELAY = 250;

  var IGNORE_KEYCODES = [9,12,13,16,17,18,19,20,32,33,34,35,36,37,38,39,40,45,91,92,93,112,113,114,115,116,117,118,119,120,121,122,123,144,145,224];

  var RETURN = 13;

  var CONCEPT_LIST = [
    {
      "id": 1,
      "name": "Common cold",
      "description": "Respiratory tract infection, upper",
      "datatype": "Numeric",
      "selected": false
    },
    {
      "id": 2,
      "name": "Pulmonary Effusion",
      "description": "Accumulation of serous, purulent, or bloody fluid into the pulmonary cavity.",
      "datatype": "Numeric",
      "selected": false
    },
    {
      "id": 3,
      "name": "Vitamin C Deficiency",
      "description": "A disease caused by a lack of vitamin C and characterized by spongy gums, loosening of the teeth, and bleeding into the skin and mucous membranes.",
      "datatype": "Numeric",
      "selected": false
    }
  ];

  var currTimeout;

  function doSearch() {
    $scope.$apply($scope.concepts = []);
    var query = $scope.query;
    for (var i in CONCEPT_LIST) {
      var currRow = CONCEPT_LIST[i];
      if (currRow.name.toLowerCase().indexOf(query.toLowerCase()) != -1 || currRow.description.toLowerCase().indexOf(query.toLowerCase()) != -1) {
        $scope.$apply(function(){
          $scope.concepts.push(CONCEPT_LIST[i]);
        });
      }
    }
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
    var string = "Adding: ";
    for (var i in concepts) {
      string += concepts[i].name + ", ";
    }
    alert(string);
    window.location.hash="/";
  };

  $scope.cancel = function() {
    window.location.hash="/";
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

}
