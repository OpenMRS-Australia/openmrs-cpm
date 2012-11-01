define(['cpm'], function(cpm) {
  cpm.controller('ListProposalsCtrl', ['$scope', 'Proposals', function($scope, Proposals) {
    document.title = 'Manage Concept Proposals';
    $scope.proposals = Proposals.query();
  }]);
});
