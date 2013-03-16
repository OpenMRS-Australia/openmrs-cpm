define(['cpm-review', 'config'], function(module, config) {
    module.controller('ReviewProposalCtrl', ['$scope', '$routeParams', 'ProposalReviews', function($scope, $routeParams, ProposalReviews) {

        var proposalId = $routeParams.proposalId;
        $scope.isLoading = true;
        $scope.resourceLocation = config.resourceLocation;

        $scope.proposal = ProposalReviews.get({proposalId: proposalId}, function() {
            $scope.isLoading = false;
        });

    }])
});