
cpm.controller('CreateConceptProposalCtrl', ['$scope', function CreateConceptProposalCtrl($scope) {

  $scope.selectedConcepts = [];

  $scope.emailErrorMsg = function() {
    if ($scope.form.email.$dirty && $scope.form.email.$invalid) {
      return "Please specify a valid email address";
    } else {
      return "";
    }
  };

  $scope.save = function() {
    alert("Saving: email: " + $scope.email + ", description: " + $scope.description);
  };

  $scope.send = function() {
    alert("Sending proposal");
  };

  $scope.delete = function() {
    if (confirm("Are you sure?")) {
      alert("Deleting proposal");
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
