
function CreateConceptProposalCtrl($scope) {

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

  $scope.addConcepts = function() {
    alert("Opening dialog with new view to select concepts");
  };

}
