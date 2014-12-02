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
      function($scope, $routeParams, $location, $window, ProposalReviewConcepts, Menu, $http) {

        var proposalId = $routeParams.proposalId;
        var conceptId = $routeParams.conceptId;
        $scope.isLoading = true;
        $scope.contextPath = config.contextPath;
        $scope.resourceLocation = config.resourceLocation;
        $scope.decisionMade = false;
        $scope.isDeciding = false;
        $scope.menu = Menu.getMenu();

        $scope.acceptConcept = function(concepts) {
          if (concepts.length > 0) {
            $scope.concept.conceptId = concepts[0].id;
          }
          $scope.isDeciding = true;
          $scope.concept.$update({proposalId: proposalId}, function(){
            $scope.showProposal();
          }, function(){
            $scope.isDeciding = false;
            $window.alert('Error saving. Please try again');
          });
          $scope.isSearchDialogOpen = false;
        };

        $scope.loadConcept = function() {
          $scope.concept = ProposalReviewConcepts.get({ proposalId: proposalId, conceptId: conceptId }, function() {
            $http.get('/openmrs/ws/conceptreview/userDetails', {})
              .success(function(data) {
                data = data || {};
                $scope.concept.newCommentName = data.name;
                $scope.concept.newCommentEmail = data.email;
              });
            $scope.decisionMade = ($scope.concept.status !== 'RECEIVED');
            $scope.isLoading = false;
          });
        };
        $scope.loadConcept();

        $scope.showProposal = function() {
          $location.path('/edit/' + proposalId);
        };

        $scope.addComment = function() {
          $scope.concept.$update({proposalId: proposalId}, function(){
            $window.alert('Comment Saved');
          }, function(){
            $window.alert('Error saving comment');
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
          $scope.isDeciding = true;
          $scope.concept.$update({proposalId: proposalId}, function(){
            $scope.showProposal();
          }, function(){
            $scope.isDeciding = false;
            $window.alert('Error saving. Please try again');
          });
        };
        $scope.resetStatus = function() {
          $scope.concept.status = 'RECEIVED';
          $scope.concept.conceptId = 0;
          $scope.isDeciding = true;
          $scope.concept.$update({proposalId: proposalId}, function(){
            $scope.loadConcept();
            $scope.isDeciding = false;
            $window.alert('Status cleared');
          }, function(){
            $scope.isDeciding = false;
            $window.alert('Error saving. Please try again');
          });
        };
      });
  });
