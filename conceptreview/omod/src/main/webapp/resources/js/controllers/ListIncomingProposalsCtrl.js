define([
    'angular',
    'config',
    'js/services/proposalReviews',
    './index'
  ],
  function(angular, config) {

    'use strict';

    angular.module('conceptreview.controllers')
      .controller('ListIncomingProposalsCtrl',
        function($scope, $location, ProposalReviews, Menu) {

          document.title = 'Review Concept Proposals';
          $scope.contextPath = config.contextPath;
          $scope.resourceLocation = config.resourceLocation;
          $scope.responseReceived = false;

          $scope.menu = Menu.getMenu(1);

          $scope.proposals = ProposalReviews.query(function() {
            $scope.responseReceived = true;
          });

          $scope.editProposal = function(proposalId) {
            $location.path('/edit/' + proposalId);
          };
        }
      );
  }
);