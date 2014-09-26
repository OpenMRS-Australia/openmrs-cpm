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
      function($scope, $routeParams, $location, ProposalReviewConcepts, Menu) {

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
            alert("Error saving. Please try again");
          });
          $scope.isSearchDialogOpen = false;
        };

        $scope.concept = ProposalReviewConcepts.get({ proposalId: proposalId, conceptId: conceptId }, function() {
          $scope.isLoading = false;
        });

        $scope.showConcept = function(conceptId) {
          $location.path('/edit/' + $scope.proposal.id + '/concept/' + conceptId);
        };

        $scope.showProposal = function() {
          $location.path('/edit/' + proposalId);
        };

        $scope.saveReviewComment = function() {
          var d = new Date();
          var dformat = [d.getFullYear(), ("00" + (d.getMonth() + 1)).slice(-2), ("00" + d.getDate()).slice(-2)].join('/')+' '+
            [("00" + d.getHours()).slice(-2), ("00" + d.getMinutes()).slice(-2), ("00" + d.getSeconds()).slice(-2)].join(':');
          if(!$scope.concept.reviewDiscussion) $scope.concept.reviewDiscussion = "";
          if($scope.concept.reviewDiscussion) $scope.concept.reviewDiscussion += "\n";

          $scope.concept.reviewDiscussion += "" +
            "=======================================================\n" +
            dformat + " " + $scope.newReviewCommentName + " (" + $scope.newReviewCommentEmail + ")\n" +
            "-------------------------------------------------------\n" +
            $scope.newReviewComment +
            "";
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
            alert("Error saving. Please try again");
          });
        };
      });
  });
