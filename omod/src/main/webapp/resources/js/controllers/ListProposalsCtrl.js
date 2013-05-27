define(['./index', 'config'], function(controllers, config) {

  controllers.controller('ListProposalsCtrl', ['$scope', 'Proposals', '$location', function($scope, Proposals, $location) {

    document.title = 'Manage Concept Proposals';
    $scope.contextPath = config.contextPath;
    $scope.resourceLocation = config.resourceLocation;
    $scope.responseReceived = false;

    $scope.proposals = Proposals.query(function() {
      $scope.responseReceived = true;
    });

    $scope.editProposal = function(proposalId) {
      $location.path('/edit/' + proposalId)
    }
  }]);
});
