define(['config', 'js/services/searchConcept'], function(config) {

    var searchConceptsCtrl = function($scope, $timeout, SearchConcept) {

        var SEARCH_DELAY = 250;
        var searchTimeout;

        $scope.currentRequestNum = 0;
        $scope.contextPath = config.contextPath;
        $scope.isMultiple = true;
        $scope.concepts = [];

        $scope.$on('InitSearchConceptsDialog', function(e, isMultiple) {

            $scope.isMultiple = isMultiple;
        });


        var getSelectedConcepts = function() {

            if ($scope.isMultiple) {
                return $scope.concepts.filter(function(e) {
                    return e.selected;
                });
            } else {
                return [$scope.selectedConcept];
            }
        };

        var mergeNames = function(names, preferredName) {

            return names.reduce(function(prev, curr) {
                var conceptName = curr.name;
                if (conceptName !== "" &&
                        conceptName !== preferredName) {
                    if (prev == "") {
                        return conceptName;
                    } else {
                        return prev + ", " + conceptName;
                    }
                } else {
                    return prev;
                }
            }, "");
        };

        $scope.processSearchResults = function(response) {
            $scope.isSearching = false;
            
            var data = response.data;

            if (parseInt(data.requestNum) >= $scope.currentRequestNum) {
                $scope.currentRequestNum = data.requestNum;
                $scope.concepts = data.concepts;
                
                $scope.concepts.forEach(function(concept) {
                    if (concept.names)
                        concept.synonyms = mergeNames(concept.names,
                            concept.preferredName);
                });
            }
        };

        $scope.search = function() {

            if (typeof searchTimeout !== "undefined") {
                $timeout.cancel(searchTimeout);
            }

            searchTimeout = $timeout(function() {
                
                $scope.currentRequestNum++;
                $scope.isSearching = true;
                SearchConcept.runQuery($scope.searchTerm, $scope.currentRequestNum)
                        .then($scope.processSearchResults);
            }, SEARCH_DELAY);
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

            $scope.$emit('AddConceptButtonClicked', concepts);
        };

        $scope.cancel = function() {

            $scope.$emit('CloseSearchConceptsDialog');
        };
    };

    return searchConceptsCtrl;
});