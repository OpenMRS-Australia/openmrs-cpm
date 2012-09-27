function SearchConceptsDialogCtrl($scope) {

  var SEARCH_DELAY = 250;

  var IGNORE_KEYCODES = [9,12,13,16,17,18,19,20,32,33,34,35,36,37,38,39,40,45,91,92,93,112,113,114,115,116,117,118,119,120,121,122,123,144,145,224];

  var RETURN = 13;

  var currTimeout;

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
        alert("searching after delay");
      }, SEARCH_DELAY);

    // trigger search immediately
    } else if (which == RETURN) {
      if (typeof currTimeout != "undefined") {
        clearTimeout(currTimeout)
      }
      alert("searching now");
    }
  };

  $scope.add = function() {
    alert("add");
  };

  $scope.cancel = function() {
    alert("cancel");
  };
}
