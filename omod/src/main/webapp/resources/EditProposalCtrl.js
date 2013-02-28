define(['cpm', 'config'], function(cpm, config) {
  cpm.controller('EditProposalCtrl', ['$scope', '$routeParams', 'Proposals', '$location', function($scope, $routeParams, Proposals, $location) {

    var proposalId = $routeParams.proposalId;
    $scope.isEdit = typeof proposalId !== 'undefined';
    $scope.isSubmitting = false;

    // XXX
    if ($scope.isEdit) {
      document.title = 'Edit Concept Proposal';
    } else {
      document.title = 'Create Concept Proposal';
    }

    $scope.resourceLocation = config.resourceLocation;


    if ($scope.isEdit) {
      $scope.proposal = Proposals.get({proposalId: proposalId});
    } else {
      $scope.proposal = new Proposals();
      $scope.proposal.concepts= [];

    }

    $scope.nameErrorMsg = function() {
      if ($scope.form.name.$dirty && $scope.form.name.$invalid) {
        return "Name is required";
      }
      return "";
    };

    $scope.emailErrorMsg = function() {
      if ($scope.form.email.$dirty && $scope.form.email.$invalid) {
        return "Please specify a valid email address";
      } else {
        return "";
      }
    };

    $scope.save = function() {
			//$scope.proposal.concepts=$scope.selectedConcepts;

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

    $scope.submit = function() {
      $scope.proposal.status = 'TBS';
      $scope.proposal.$update(function() {
        $scope.isSubmitting = false;
      }, function() {
        $scope.isSubmitting = false;
      });
      $scope.isSubmitting = true;
    };

    $scope.deleteProposal = function() {
      if (confirm("Are you sure?")) {
        $scope.proposal.$remove();
        $location.path('/');
      }
    };

    $scope.removeConcept = function(concept) {
      if (confirm("Are you sure?")) {
        for (var i in $scope.proposal.concepts) {
          if ($scope.proposal.concepts[i] == concept) {
            delete $scope.proposal.concepts[i];
            $scope.proposal.concepts.splice(i, 1);
          }
        }
      }
    }
  }]);
});
