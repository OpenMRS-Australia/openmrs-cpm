define(['cpm-review', 'config'], function(module, config) {
    module.controller('ReviewConceptCtrl', ['$scope', '$routeParams', 'ProposalReviewConcepts', function($scope, $routeParams, ProposalReviewConcepts) {

        var proposalId = $routeParams.proposalId;
        var conceptId = $routeParams.conceptId;
        $scope.isLoading = true;
        $scope.contextPath = config.contextPath;

        $scope.resourceLocation = config.resourceLocation;

        $scope.concept = ProposalReviewConcepts.get({proposalId: proposalId, conceptId: conceptId}, function() {
            $scope.isLoading = false;
        });

        $scope.showConcept = function(conceptId) {
            $location.path('/edit/' + $scope.proposal.id + '/concept/' + conceptId);
        };

        $scope.conceptCreated = function() {
            $scope.concept.status = 'CLOSED_NEW';

            $scope.dialog='open';

            $scope.concept.$update({proposalId: proposalId});
        }

        $scope.conceptExists = function() {
            $scope.concept.status = 'CLOSED_EXISTING';
            $scope.concept.$update({proposalId: proposalId});
        }

        $scope.conceptRejected = function() {
            $scope.concept.status = 'CLOSED_REJECTED';
            $scope.concept.$update({proposalId: proposalId});
        }

    }])
});