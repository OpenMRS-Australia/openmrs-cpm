define(['cpm-review', 'config'], function(cpm, config) {
  cpm.controller('ListIncomingProposalsCtrl', ['$scope', 'ProposalResponses', '$location', function($scope, ProposalResponses, $location) {

    document.title = 'Incoming Concept Proposals';
    $scope.contextPath = config.contextPath;
    $scope.responseReceived = false;

    $scope.proposals = ProposalResponses.query(function() {
      $scope.responseReceived = true;
    });

    $scope.editProposal = function(proposalId) {
      $location.path('/edit/' + proposalId)
    }
  }]);
});
