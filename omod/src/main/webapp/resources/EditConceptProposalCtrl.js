define(['cpm', 'config'], function(cpm, config) {
  cpm.controller('EditConceptProposalCtrl', ['$scope', '$routeParams', '$http', '$location', function EditConceptProposalCtrl($scope, $routeParams, $http, $location) {

    var proposalId = $routeParams.proposalId;
    $scope.isEdit = typeof proposalId !== 'undefined';

    $scope.resourceLocation = config.resourceLocation;

    $scope.selectedConcepts = [];

    if ($scope.isEdit) {
      $http.get('/openmrs/module/cpm/rest/proposals/' + proposalId + '.form').success(function(data) {
        $scope.proposal = data;
      });
    }

    $scope.emailErrorMsg = function() {
      if ($scope.form.email.$dirty && $scope.form.email.$invalid) {
        return "Please specify a valid email address";
      } else {
        return "";
      }
    };

    $scope.save = function() {
      $http.put('/openmrs/module/cpm/rest/proposals/' + proposalId + '.form', $scope.proposal).success(function(data) {
        // navigate to edit url or not?
        // will fetch extra data but url will be up to date
        if (!$scope.isEdit) {
          $location.path('/edit/' + data);
        } else {
          alert("Saved!");
        }
      });
    };

    $scope.send = function() {
      alert("Sending proposal");
    };

    $scope.delete = function() {
      if (confirm("Are you sure?")) {
        $http.delete('/openmrs/module/cpm/rest/proposals/' + proposalId + '.form').success(function() {
          $location.path('/');
        });
      }
    };

    $scope.removeConcept = function(concept) {
      if (confirm("Are you sure?")) {
        for (var i in $scope.selectedConcepts) {
          if ($scope.selectedConcepts[i] == concept) {
            delete $scope.selectedConcepts[i];
            $scope.selectedConcepts.splice(i, 1);
          }
        }
      }
    }
  }]);
});
