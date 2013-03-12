define(['cpm', 'config'], function(cpm, config) {
  cpm.controller('EditProposalCtrl', ['$scope', '$routeParams', 'Proposals', '$location', function($scope, $routeParams, Proposals, $location) {

    $scope.contextPath = config.contextPath;

    var proposalId = $routeParams.proposalId;
    $scope.isEdit = typeof proposalId !== 'undefined';
    $scope.isSubmitting = false;
    $scope.isLoading = $scope.isEdit ? true : false;

    // XXX
    if ($scope.isEdit) {
      document.title = 'Edit Concept Proposal';
    } else {
      document.title = 'Create Concept Proposal';
    }

    $scope.resourceLocation = config.resourceLocation;


    if ($scope.isEdit) {
      $scope.proposal = Proposals.get({proposalId: proposalId}, function() {
          $scope.isLoading = false;
      });
    } else {
      $scope.proposal = new Proposals();
      $scope.proposal.status = 'DRAFT';
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

      $scope.isLoading = true;
      if ($scope.isEdit) {
        $scope.proposal.$update(function() {
          $scope.isLoading = false;
          alert("Saved!");
        });
      } else {
        $scope.proposal.$save(function() {
          // navigate to edit url or not?
          // will fetch extra data but url will be up to date
          $location.path('/edit/' + $scope.proposal.id);
          $scope.isLoading = false;
        });
      }
    };

    $scope.submit = function() {
      $scope.proposal.status = 'TBS';
      $scope.proposal.$update(function() {
        $scope.isSubmitting = false;
        $scope.isLoading = false;
      }, function() {
        $scope.isSubmitting = false;
        $scope.isLoading = false;
      });
      $scope.isSubmitting = true;
      $scope.isLoading = true;
    };

    $scope.deleteProposal = function() {
      if (confirm("Are you sure?")) {
        $scope.isLoading = true;
        $scope.proposal.$remove(function() {
          $location.path('/');
          $scope.isLoading = false;
        });
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
