define(['./index', 'config', 'js/services/services'], function(controllers, config) {
  controllers.controller('EditProposalCtrl', ['$scope', '$routeParams', 'Proposals', 'Menu', '$location', function($scope, $routeParams, Proposals, MenuService, $location) {

    $scope.contextPath = config.contextPath;
    $scope.resourceLocation = config.resourceLocation;

    var proposalId = $routeParams.proposalId;
    $scope.isEdit = typeof proposalId !== 'undefined';
    $scope.isSubmitting = false;
    $scope.isLoading = $scope.isEdit ? true : false;
    $scope.isReadOnly = true;

    $scope.menu = MenuService.getMenu(1);

    $scope.$on('AddConceptButtonClicked', function(e, concepts) {
      $scope.proposal.concepts = $scope.proposal.concepts.concat(concepts);
      $scope.dialog = 'close';
    });

    $scope.$on('CloseSearchConceptsDialog', function() {
      $scope.dialog = 'close';
    });


    // XXX
    if ($scope.isEdit) {
      document.title = 'Edit Concept Proposal';
    } else {
      document.title = 'Create Concept Proposal';
    }


    if ($scope.isEdit) {
      $scope.proposal = Proposals.get({proposalId: proposalId}, function() {
          $scope.isLoading = false;
          $scope.isReadOnly = $scope.proposal.status != 'DRAFT';
      });
    } else {
      $scope.proposal = new Proposals();
      $scope.proposal.status = 'DRAFT';
      $scope.proposal.concepts= [];

      $scope.isReadOnly = false;
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
          alert("Saved!");
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
