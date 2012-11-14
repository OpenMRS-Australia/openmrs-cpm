define(['cpm', 'config'], function(cpm, config) {
  cpm.controller('ListProposalsCtrl', ['$scope', 'Proposals', function($scope, Proposals) {
    document.title = 'Manage Concept Proposals';
    $scope.contextPath = config.contextPath;
    $scope.responseReceived = false;
    $scope.proposals = Proposals.query(function() {
      $scope.responseReceived = true;
    });
  }]);
});
