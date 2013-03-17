define(['cpm-review', 'config'], function(module, config) {
    module.controller('ReviewConceptCtrl', ['$scope', '$routeParams', 'ProposalReviews', function($scope, $routeParams, ProposalReviews) {

        var proposalId = $routeParams.proposalId;
        var conceptId = $routeParams.conceptId;
        $scope.isLoading = true;
        $scope.resourceLocation = config.resourceLocation;

        $scope.proposal = ProposalReviews.get({proposalId: proposalId}, function() {
            $scope.isLoading = false;
            $scope.concept = $scope.proposal.concepts[conceptId];
        });

        $scope.showConcept = function(conceptId) {
            $location.path('/edit/' + $scope.proposal.id + '/concept/' + conceptId);
        };

    }])
});