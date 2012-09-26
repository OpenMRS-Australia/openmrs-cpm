
function CreateConceptProposalCtrl($scope) {

  $scope.save = function() {

    alert("Saving: email: " + $scope.email + ", description: " + $scope.description);

  }

  $scope.send = function() {
    alert("Sending proposal");
  }

  $scope.delete = function() {
    alert("Deleting proposal");
  }

}
