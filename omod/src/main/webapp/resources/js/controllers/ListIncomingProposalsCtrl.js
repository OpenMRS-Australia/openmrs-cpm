define(['./index', 'config'], function(controllers, config) {

  controllers.controller('ListIncomingProposalsCtrl', ['$scope', 'ProposalReviews', 'Menu', '$location', function($scope, ProposalReviews, MenuService, $location) {

    document.title = 'Incoming Concept Proposals';
    $scope.contextPath = config.contextPath;
    $scope.resourceLocation = config.resourceLocation;
    $scope.responseReceived = false;

    $scope.menu = MenuService.getMenu(3);

    $scope.proposals = ProposalReviews.query(function() {
      $scope.responseReceived = true;
    });

    $scope.editProposal = function(proposalId) {
      $location.path('/edit/' + proposalId)
    }
  }]);
});
