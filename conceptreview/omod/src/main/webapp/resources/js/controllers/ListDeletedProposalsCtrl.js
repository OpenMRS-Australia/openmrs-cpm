define([
    'angular',
    'config',
    'js/services/deletedProposalReviews',
    './index'
  ],
  function(angular, config) {

    'use strict';

    angular.module('conceptreview.controllers')
      .controller('ListDeletedProposalsCtrl',
        function($scope, $location, DeletedProposalReviews, Menu) {

          document.title = 'Deleted Concept Proposals';
          $scope.contextPath = config.contextPath;
          $scope.resourceLocation = config.resourceLocation;
          $scope.responseReceived = false;

          $scope.menu = Menu.getMenu(1);

          $scope.proposals = DeletedProposalReviews.query(function() {
            $scope.responseReceived = true;
          });

          $scope.editProposal = function(proposalId) {
            $location.path('/deleted/' + proposalId);
          };
        }
      );
  }
);