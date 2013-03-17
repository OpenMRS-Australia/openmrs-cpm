define(['cpm-review', 'config'], function(module, config) {
    module.controller('ReviewConceptCtrl', ['$scope', '$routeParams', 'ProposalReviews', function($scope, $routeParams, ProposalReviews) {

        var proposalId = $routeParams.proposalId;
        $scope.isLoading = true;
        $scope.resourceLocation = config.resourceLocation;

        $scope.proposal = ProposalReviews.get({proposalId: proposalId}, function() {
            $scope.isLoading = false;
        });

        $scope.showConcept = function(conceptId) {
            $location.path('/edit/' + $scope.proposal.id + '/concept/' + conceptId);
        };

    }])
});