define(['cpm'], function(cpm) {
  cpm.controller('ListConceptProposalsCtrl', ['$scope', '$http', function($scope, $http) {

    $scope.proposals = [];

    $http.get('/openmrs/module/cpm/rest/proposals.list').success(function(data){
      $scope.proposals = data;
    });

  }]);
});
