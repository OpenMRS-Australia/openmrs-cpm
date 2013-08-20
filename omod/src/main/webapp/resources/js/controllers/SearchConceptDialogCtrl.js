define(['./index', 'config', 'js/services/searchConcept', 'js/directives/jqueryUiDialog'], 
function(controllers, config) {
    controllers.controller('SearchConceptDialogCtrl',
            function($scope, $timeout, SearchConcept) {
                var SEARCH_DELAY = 250;
                var searchTimeout;

                if ($scope.isMultiple) {
                    $scope.title = "Add concept";
                } else {
                    $scope.title = "Select a concept";
                }

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

                        _.forEach($scope.concepts, function(concept) {
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
                    $scope.onAcceptFn(concepts);
                };

                $scope.onClose = function() {
                    $scope.isOpen = false;
                };
            });
});