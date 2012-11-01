define(['cpm', 'config'], function(cpm, config) {
  cpm.controller('EditProposalCtrl', ['$scope', '$routeParams', 'Proposals', '$location', function($scope, $routeParams, Proposals, $location) {

    var proposalId = $routeParams.proposalId;
    $scope.isEdit = typeof proposalId !== 'undefined';

    if ($scope.isEdit) {
      document.title = 'Edit Concept Proposal';
    } else {
      document.title = 'Create Concept Proposal';
    }

    $scope.resourceLocation = config.resourceLocation;

    $scope.selectedConcepts = [];

    if ($scope.isEdit) {
      $scope.proposal = Proposals.get({id: proposalId});
    } else {
      $scope.proposal = new Proposals();
    }

    $scope.emailErrorMsg = function() {
      if ($scope.form.email.$dirty && $scope.form.email.$invalid) {
        return "Please specify a valid email address";
      } else {
        return "";
      }
    };

    $scope.save = function() {
      if ($scope.isEdit) {
        $scope.proposal.$update(function() {
          alert("Saved!");
        });
      } else {
        $scope.proposal.$save(function() {
          // navigate to edit url or not?
          // will fetch extra data but url will be up to date
          $location.path('/edit/' + $scope.proposal.id);
        });
      }
    };

    $scope.send = function() {
      alert("Sending proposal");
    };

    $scope.deleteProposal = function() {
      if (confirm("Are you sure?")) {
        $scope.proposal.$remove();
        $location.path('/');
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
