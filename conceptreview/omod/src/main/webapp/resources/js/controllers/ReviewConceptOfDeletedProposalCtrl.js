define([
    'angular',
    'config',
    'js/services/deletedProposalReviewConcepts',
    'js/services/menu',
    'js/services/searchConcept',
    'js/directives/searchConceptDialog',
    'js/directives/jqueryUiDialog',
    './index'
  ], function(angular, config) {

    'use strict';
    angular.module('conceptreview.controllers').controller('ReviewConceptOfDeletedProposalCtrl',
      function($scope, $routeParams, $location, $window, DeletedProposalReviewConcepts, Menu, $http) {

        var proposalId = $routeParams.proposalId;
        var conceptId = $routeParams.conceptId;
        $scope.isLoading = true;
        $scope.contextPath = config.contextPath;
        $scope.resourceLocation = config.resourceLocation;
        $scope.decisionMade = false;
        $scope.menu = Menu.getMenu();

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
          $location.path('/deleted/' + proposalId);
        };
      });
  });
