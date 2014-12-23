define([
    'angular',
    'config',
    'js/services/proposalReviews',
    'js/filters/proposalStatus',
    './index'
  ],
  function(angular, config) {

    'use strict';

    angular.module('conceptreview.controllers').controller('ReviewProposalCtrl',
      function($scope, $routeParams, $location, $window, ProposalReviews, Menu) {

/* We need to add a status tag in this */

        var proposalId = $routeParams.proposalId;
        $scope.isLoading = true;
        $scope.contextPath = config.contextPath;
        $scope.resourceLocation = config.resourceLocation;
        $scope.isDeleted = false;

        $scope.menu = Menu.getMenu();

        $scope.proposal = ProposalReviews.get({proposalId: proposalId}, function() {
          $scope.isLoading = false;
          $scope.isDeleted = ($scope.proposal.status === 'DELETED');
        });

        $scope.showConcept = function(conceptId) {
          $location.path('/edit/' + $scope.proposal.id + '/concept/' + conceptId);
        };

        $scope.delete = function() {
          if ($window.confirm('Are you sure?')) {
            $scope.isLoading = true;
            $scope.proposal.$remove(function() {
              $scope.isLoading = false;
              $location.path('/');
            });
          }
        };
      });
  }
);