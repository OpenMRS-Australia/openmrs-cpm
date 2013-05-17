define(['config'], function(config) {

    var searchConceptsCtrl = ['$scope', '$http', '$timeout', function($scope, $http, $timeout) {

        $scope.contextPath = config.contextPath;

        var SEARCH_DELAY = 250;

        var IGNORE_KEYCODES = [9,12,13,16,17,18,19,20,32,33,34,35,36,37,38,39,40,45,91,92,93,112,113,114,115,116,117,118,119,120,121,122,123,144,145,224];

        var RETURN = 13;

        var currTimeout;

        $scope.isMultiple = true;
        $scope.$on('InitSearchConceptsDialog', function(e, isMultiple) {
            $scope.isMultiple = isMultiple;
        });

        $scope.conceptClicked = function(concept) {
            if ($scope.isMultiple) {
                concept.selected = !concept.selected;
            } else {
                $scope.selectedConcept = concept;
            }
        }

        function doSearch() {
            $scope.isSearching = true;
            $http.get(config.contextPath + '/ws/cpm/concepts?query=' + encodeURIComponent($scope.query)).success(function(data) {
                $scope.isSearching = false;
                $scope.concepts = data;
                for (var i in $scope.concepts) {
                    var concept = $scope.concepts[i];
                    concept.synonyms = "";
                    for (var j in concept.names) {
                        var name = concept.names[j].name;
                        if (name !== concept.preferredName) {
                            if (concept.synonyms !== "") {
                                concept.synonyms += ", ";
                            }
                            concept.synonyms += name;
                        }
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

            $scope.$emit('AddConceptButtonClicked', concepts);

//            $scope.$parent.$parent.proposal.concepts = $scope.$parent.$parent.proposal.concepts.concat(concepts);
//            $scope.$parent.$parent.dialog = "close";
        };

        $scope.cancel = function() {

            $scope.$emit('CloseSearchConceptsDialog');
//            $scope.$parent.$parent.dialog = "close";
        };

        $scope.concepts = [];

        function getSelectedConcepts() {
            var selectedList = [];

            if ($scope.isMultiple) {
                for (var i in $scope.concepts) {
                    if ($scope.concepts[i].selected) {
                        selectedList[selectedList.length] = $scope.concepts[i];
                    }
                }
            } else {
                selectedList.push($scope.selectedConcept)
            }

            return selectedList;
        }

    }];

    return searchConceptsCtrl;

});