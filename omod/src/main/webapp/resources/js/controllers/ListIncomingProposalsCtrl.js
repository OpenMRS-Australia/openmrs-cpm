define([
    'angular',
    'config',
    './index'
  ],
  function(angular, config) {

    'use strict';

    angular.module('cpm.controllers')
      .controller('ListIncomingProposalsCtrl',
        function($scope, $location, ProposalReviews, MenuService) {

          document.title = 'Incoming Concept Proposals';
          $scope.contextPath = config.contextPath;
          $scope.resourceLocation = config.resourceLocation;
          $scope.responseReceived = false;

          $scope.menu = MenuService.getMenu(3);

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