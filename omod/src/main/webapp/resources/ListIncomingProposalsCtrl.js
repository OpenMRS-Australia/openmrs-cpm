define(['cpm-review', 'config'], function(cpm, config) {
  cpm.controller('ListIncomingProposalsCtrl', ['$scope', 'ProposalReviews', '$location', function($scope, ProposalReviews, $location) {

    document.title = 'Incoming Concept Proposals';
    $scope.contextPath = config.contextPath;
    $scope.resourceLocation = config.resourceLocation;
    $scope.responseReceived = false;

    $scope.proposals = ProposalReviews.query(function() {
      $scope.responseReceived = true;
    });

    $scope.editProposal = function(proposalId) {
      $location.path('/edit/' + proposalId)
    }
  }]);
});
