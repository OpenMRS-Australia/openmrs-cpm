define([
  './index',
  'config',
  'js/services/services',
  'js/services/menu',
  'js/services/alerts'
], function(controllers, config) {

  'use strict';

  controllers.controller('EditProposalCtrl',
    function($scope, $routeParams, $location, Proposals, Menu, Alerts) {

      $scope.contextPath = config.contextPath;
      $scope.resourceLocation = config.resourceLocation;

      var proposalId = $routeParams.proposalId;
      $scope.isEdit = typeof proposalId !== 'undefined';
      $scope.isSubmitting = false;
      $scope.isLoading = $scope.isEdit ? true : false;
      $scope.isReadOnly = true;

      $scope.menu = Menu.getMenu(1);

      $scope.getConceptUnion = function(concepts, existingConcepts) {
        return _.uniq(_.union(concepts, existingConcepts), false, function(concept) { 
            return concept.id; 
        });
      }

      $scope.$on('AddConceptButtonClicked', function(e, concepts) {
        $scope.proposal.concepts = $scope.getConceptUnion(concepts, $scope.proposal.concepts);
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
        var redirectUrl = '/';
        var successAlert = {message: 'Proposal successfully saved'};
        $scope.isLoading = true;
        if ($scope.isEdit) {
          Proposals.update($scope.proposal, function() {
            $location.path(redirectUrl);
            Alerts.queue(successAlert);
          });
        } 
        else {
          Proposals.save($scope.proposal, function() {
            $location.path(redirectUrl);
            Alerts.queue(successAlert);
          });
        }
      };

      $scope.submit = function() {
        $scope.proposal.status = 'TBS';

        var setInFlight = function() {
          $scope.isSubmitting = true;
          $scope.isLoading = true;
        };

        var cancelInFlight = function() {
          $scope.isSubmitting = false;
          $scope.isLoading = false;
        };

        var flightLanded = function() {
          Alerts.queue({message: 'Proposal successfully submitted'});
          $location.path('/');
        }

        setInFlight();
        if (typeof $scope.proposal.id === 'undefined') {
          $scope.proposal.$save(flightLanded, cancelInFlight);
        } 
        else {
          $scope.proposal.$update(flightLanded, cancelInFlight);
        }
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
