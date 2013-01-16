define(['cpm-review', 'config'], function(cpm, config) {
  cpm.controller('ListIncomingProposalsCtrl', ['$scope', 'ProposalResponses', function($scope, ProposalResponses) {
    document.title = 'Incoming Concept Proposals';
    $scope.contextPath = config.contextPath;
    $scope.responseReceived = false;
    $scope.proposals = ProposalResponses.query(function() {
      $scope.responseReceived = true;
    });
  }]);
});
