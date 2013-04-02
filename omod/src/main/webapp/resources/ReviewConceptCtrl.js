define(['cpm-review', 'config'], function(module, config) {
    module.controller('ReviewConceptCtrl', ['$scope', '$routeParams', 'ProposalReviewConcepts', function($scope, $routeParams, ProposalReviewConcepts) {

        var proposalId = $routeParams.proposalId;
        var conceptId = $routeParams.conceptId;
        $scope.isLoading = true;
        $scope.contextPath = config.contextPath;

        $scope.concept = ProposalReviewConcepts.get({proposalId: proposalId, conceptId: conceptId}, function() {
            $scope.isLoading = false;
        });

        $scope.showConcept = function(conceptId) {
            $location.path('/edit/' + $scope.proposal.id + '/concept/' + conceptId);
        };

        $scope.conceptCreated = function() {
            $scope.concept.status = 'CLOSED_NEW';
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

        $scope.saveComment = function(comment) {
            $scope.concept.comment = comment;
            $scope.concept.$update({proposalId: proposalId});
        }

    }])
});