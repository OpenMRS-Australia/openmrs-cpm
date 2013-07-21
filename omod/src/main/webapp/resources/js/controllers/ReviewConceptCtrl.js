define([
    './index',
    'config',
    'js/services/services',
    'js/services/menu'
], function(controllers, config) {

    'use strict';

    controllers.controller('ReviewConceptCtrl',

        function($scope, $routeParams, ProposalReviewConcepts, Menu) {

            var proposalId = $routeParams.proposalId;
            var conceptId = $routeParams.conceptId;
            $scope.isLoading = true;
            $scope.contextPath = config.contextPath;
            $scope.resourceLocation = config.resourceLocation;

            $scope.menu = Menu.getMenu();

            $scope.$on('AddConceptButtonClicked', function(e, concepts) {
                if (concepts.length > 0) {
                    $scope.concept.conceptId = concepts[0].id;
                }
                $scope.concept.$update({proposalId: proposalId});
                $scope.dialog = 'close';
            });

            $scope.$on('CloseSearchConceptsDialog', function() {
                $scope.dialog = 'close';
            });

            $scope.concept = ProposalReviewConcepts.get({proposalId: proposalId, conceptId: conceptId}, function() {
                $scope.isLoading = false;
            });

            $scope.showConcept = function(conceptId) {
                $location.path('/edit/' + $scope.proposal.id + '/concept/' + conceptId);
            };

            $scope.saveReviewComment = function() {
                $scope.concept.$update({proposalId: proposalId});
            };

            $scope.conceptCreated = function() {
                $scope.concept.status = 'CLOSED_NEW';
                $scope.dialog='open';
                $scope.$broadcast('InitSearchConceptsDialog', false);
            };

            $scope.conceptExists = function() {
                $scope.concept.status = 'CLOSED_EXISTING';
                $scope.dialog='open';
                $scope.$broadcast('InitSearchConceptsDialog', false);
            };

            $scope.conceptRejected = function() {
                $scope.concept.status = 'CLOSED_REJECTED';
                $scope.concept.$update({proposalId: proposalId});
            };

        }
    )
});