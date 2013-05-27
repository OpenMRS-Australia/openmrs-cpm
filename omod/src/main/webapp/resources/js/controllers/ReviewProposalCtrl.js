define(['./index', 'config'], function(controllers, config) {

    controllers.controller('ReviewProposalCtrl', ['$scope', '$routeParams', '$location', 'ProposalReviews', function($scope, $routeParams, $location, ProposalReviews) {

        var proposalId = $routeParams.proposalId;
        $scope.isLoading = true;
        $scope.contextPath = config.contextPath;
        $scope.resourceLocation = config.resourceLocation;

        $scope.proposal = ProposalReviews.get({proposalId: proposalId}, function() {
            $scope.isLoading = false;
        });

        $scope.showConcept = function(conceptId) {
            $location.path('/edit/' + $scope.proposal.id + '/concept/' + conceptId);
        };

        $scope.delete = function() {
            if (confirm('Are you sure?')) {
                $scope.isLoading = true;
                $scope.proposal.$remove(function() {
                    $scope.isLoading = false;
                    $location.path('/');
                });
            }
        }

    }])
});