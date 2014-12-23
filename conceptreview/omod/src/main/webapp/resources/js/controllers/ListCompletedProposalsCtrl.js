define([
    'angular',
    'config',
    'js/services/completedProposalReviews',
    './index'
  ],
  function(angular, config) {

    'use strict';

    angular.module('conceptreview.controllers')
      .controller('ListCompletedProposalsCtrl',
        function($scope, $location, CompletedProposalReviews, Menu) {

          document.title = 'Review Compeleted Concept Proposals';
          $scope.contextPath = config.contextPath;
          $scope.resourceLocation = config.resourceLocation;
          $scope.responseReceived = false;

          $scope.menu = Menu.getMenu(1);

          $scope.proposals = CompletedProposalReviews.query(function() {
            $scope.responseReceived = true;
          });

          $scope.editProposal = function(proposalId) {
            $location.path('/edit/' + proposalId);
          };
        }
      );
  }
);