define(['./index', 'config', 'js/services/services', 'js/services/menu'], function(controllers, config) {

  controllers.controller('ListProposalsCtrl', ['$scope', 'Proposals', 'Menu', '$location', function($scope, Proposals, MenuService, $location) {

    document.title = 'Manage Concept Proposals';
    $scope.contextPath = config.contextPath;
    $scope.resourceLocation = config.resourceLocation;
    $scope.responseReceived = false;

    $scope.menu = MenuService.getMenu(2);

    $scope.proposals = Proposals.query(function() {
      $scope.responseReceived = true;
    });

    $scope.editProposal = function(proposalId) {
      $location.path('/edit/' + proposalId)
    }
  }]);
});
