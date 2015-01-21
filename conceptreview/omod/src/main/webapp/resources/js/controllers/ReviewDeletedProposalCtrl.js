define([
    'angular',
    'config',
    'js/services/deletedProposalReviews',
    'js/filters/proposalStatus',
    './index'
  ],
  function(angular, config) {

    'use strict';

    angular.module('conceptreview.controllers').controller('ReviewDeletedProposalCtrl',
      function($scope, $routeParams, $location, $window, DeletedProposalReviews, Menu) {

/* We need to add a status tag in this */

        var proposalId = $routeParams.proposalId;
        $scope.isLoading = true;
        $scope.contextPath = config.contextPath;
        $scope.resourceLocation = config.resourceLocation;

        $scope.menu = Menu.getMenu();

        $scope.proposal = DeletedProposalReviews.get({proposalId: proposalId}, function() {
          $scope.isLoading = false;
        });

        $scope.showConcept = function(conceptId) {
          $location.path('/deleted/' + $scope.proposal.id + '/concept/' + conceptId);
        };
      });
  }
);