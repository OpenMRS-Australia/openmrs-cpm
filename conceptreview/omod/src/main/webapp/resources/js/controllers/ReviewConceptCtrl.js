define([
    'angular',
    'config',
    'js/services/proposalReviewConcepts',
    'js/services/menu',
    'js/services/searchConcept',
    'js/directives/searchConceptDialog',
    'js/directives/jqueryUiDialog',
    './index'
  ], function(angular, config) {

    'use strict';
    angular.module('conceptreview.controllers').controller('ReviewConceptCtrl',
      function($scope, $routeParams, $location, ProposalReviewConcepts, Menu, $http) {

        var proposalId = $routeParams.proposalId;
        var conceptId = $routeParams.conceptId;
        $scope.isLoading = true;
        $scope.contextPath = config.contextPath;
        $scope.resourceLocation = config.resourceLocation;

        $scope.menu = Menu.getMenu();

        $scope.acceptConcept = function(concepts) {
          if (concepts.length > 0) {
            $scope.concept.conceptId = concepts[0].id;
          }
          $scope.concept.$update({proposalId: proposalId}, function(){
            $scope.showProposal();
          }, function(){
            alert('Error saving. Please try again');
          });
          $scope.isSearchDialogOpen = false;
        };

        $scope.concept = ProposalReviewConcepts.get({ proposalId: proposalId, conceptId: conceptId }, function() {
 	      $http.get('/openmrs/ws/conceptreview/userDetails', {})
            .success(function(data) {
              data = data || {};
			  $scope.concept.newCommentName = data.name;
			  $scope.concept.newCommentEmail = data.email;
            });
          $scope.isLoading = false;
        });

        $scope.showConcept = function(conceptId) {
          $location.path('/edit/' + $scope.proposal.id + '/concept/' + conceptId);
        };

        $scope.showProposal = function() {
          $location.path('/edit/' + proposalId);
        };

        $scope.addComment = function() {
          $scope.concept.$update({proposalId: proposalId}, function(){
            alert('Comment Saved');
          }, function(){
            alert('Error saving comment');
          });
        };

        $scope.conceptCreated = function() {
          $scope.concept.status = 'CLOSED_NEW';
          $scope.isSearchDialogOpen = true;
        };

        $scope.conceptExists = function() {
          $scope.concept.status = 'CLOSED_EXISTING';
          $scope.isSearchDialogOpen = true;
        };

        $scope.conceptRejected = function() {
          $scope.concept.status = 'CLOSED_REJECTED';
          $scope.concept.$update({proposalId: proposalId}, function(){
            $scope.showProposal();
          }, function(){
            alert('Error saving. Please try again');
          });
        };
      });
  });
