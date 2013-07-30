define([
  './index',
  'config',
  'js/services/services',
  'js/services/menu'
], function(controllers, config) {

  'use strict';

  controllers.controller('EditProposalCtrl',
    function($scope, $routeParams, $location, Proposals, Menu) {

      $scope.contextPath = config.contextPath;
      $scope.resourceLocation = config.resourceLocation;

      var proposalId = $routeParams.proposalId;
      $scope.isEdit = typeof proposalId !== 'undefined';
      $scope.isSubmitting = false;
      $scope.isLoading = $scope.isEdit ? true : false;
      $scope.isReadOnly = true;

      $scope.menu = Menu.getMenu(1);

      $scope.addNewConceptsToExisting = function(concepts, existingConcepts) {
        var conceptIdList = [];

        existingConcepts.forEach(function(e) {
          return conceptIdList.push(e.id);
        })

        concepts.forEach(function(e) {
          if (conceptIdList.indexOf(e.id) === -1) existingConcepts.push(e);
        });
      }

      $scope.$on('AddConceptButtonClicked', function(e, concepts) {
        var existingConcepts = $scope.proposal.concepts;
        $scope.addNewConceptsToExisting(concepts, existingConcepts);
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
            $scope.isReadOnly = $scope.proposal.status !== 'DRAFT';
        });
      } else {
        $scope.proposal = new Proposals();
        $scope.proposal.status = 'DRAFT';
        $scope.proposal.concepts= [];

        $scope.isReadOnly = false;
      }

      $scope.save = function() {
        //$scope.proposal.concepts=$scope.selectedConcepts;
        $scope.isLoading = true;
        if ($scope.isEdit) {
          $scope.proposal.$update(function() {
            $scope.isLoading = false;
            alert('Saved!');
          });
        } else {
          $scope.proposal.$save(function() {
            // navigate to edit url or not?
            // will fetch extra data but url will be up to date
            $location.path('/edit/' + $scope.proposal.id);
            $scope.isLoading = false;
            alert('Saved!');
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
        if (confirm('Are you sure?')) {
          $scope.isLoading = true;
          $scope.proposal.$remove(function() {
            $location.path('/');
            $scope.isLoading = false;
          });
        }
      };

      $scope.removeConcept = function(concept) {
        if (confirm('Are you sure?')) {
          for (var i in $scope.proposal.concepts) {
            if ($scope.proposal.concepts[i] == concept) {
              delete $scope.proposal.concepts[i];
              $scope.proposal.concepts.splice(i, 1);
            }
          }
        }
      };
    }
  );
});
