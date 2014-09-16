define([
    'angular',
    'config',
    'js/services/searchConcept',
    'js/services/menu',
    'js/directives/searchConceptDialog',
    'js/directives/jqueryUiDialog',
    'js/services/alerts',
    './index'
  ],
  function(angular, config) {

    'use strict';

    angular.module('conceptpropose.controllers').controller('EditProposalCtrl',
      function($scope, $routeParams, $location, $window, Proposals, Menu, Alerts) {

        $scope.contextPath = config.contextPath;
        $scope.resourceLocation = config.resourceLocation;

        var proposalId = $routeParams.proposalId;
        $scope.isEdit = typeof proposalId !== 'undefined';
        $scope.isSubmitting = false;
        $scope.isLoading = true;
        $scope.isReadOnly = true;
        $scope.isSubmissible = false;

        $scope.menu = Menu.getMenu(1);

        $scope.openSearchConceptDialog = function() {
          $scope.isSearchDialogOpen = true;
        };

        $scope.getConceptUnion = function(concepts, existingConcepts) {
          return _.uniq(
            _.union(concepts, existingConcepts),
            false,
            function(concept) {
              return concept.id;
            });
        };

        $scope.acceptConcepts = function(concepts) {
          $scope.proposal.concepts = $scope.getConceptUnion(concepts, $scope.proposal.concepts);
          $scope.isSearchDialogOpen = false;
          $scope.isSubmissible = $scope.proposal.concepts.length > 0;
        };

        if ($scope.isEdit) {
          document.title = 'Edit Concept Proposal';
        } else {
          document.title = 'Create Concept Proposal';
        }

        var proposalsParams = {
          proposalId: ($scope.isEdit) ? proposalId : 'empty'
        };
        $scope.proposal = Proposals.get(proposalsParams, function() {
          $scope.isLoading = false;
          $scope.isReadOnly = $scope.proposal.status !== 'DRAFT';
          $scope.isSubmissible = $scope.proposal.concepts.length > 0;
        });

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

          var cancelInFlight = function(data) {
            $scope.proposal.status = 'DRAFT';
            $scope.isSubmitting = false;
            $scope.isLoading = false;

            if(data.status)
            {
              if(data.status === '500') {
                $window.alert('Error submitting proposal (Problem with submitting Proposal)');
              }
              else if(data.status === '401') {
                $window.alert('Error submitting proposal (Unauthorized - you need to log in)');
              }
              else {
                $window.alert('Error submitting proposal (Unknown error: ' + data.status + ')');
              }
            }
          };

          var flightLanded = function() {
            Alerts.queue({message: 'Proposal successfully submitted'});
            $location.path('/');
          };

          setInFlight();
          if ($scope.isEdit) {
            $scope.proposal.$update(flightLanded, cancelInFlight);
          }
          else {
            $scope.proposal.$save(flightLanded, cancelInFlight);
          }
        };

        $scope.deleteProposal = function() {
          if ($window.confirm('Are you sure?')) {
            $scope.isLoading = true;
            $scope.proposal.$remove(function() {
              $location.path('/');
              $scope.isLoading = false;
            });
          }
        };

        $scope.removeConcept = function(concept) {
          if ($window.confirm('Are you sure?')) {
            for (var i in $scope.proposal.concepts) {
              if ($scope.proposal.concepts[i] === concept) {
                delete $scope.proposal.concepts[i];
                $scope.proposal.concepts.splice(i, 1);
                $scope.isSubmissible = $scope.proposal.concepts.length > 0;
              }
            }
          }
        };
      }
    );
  }
);