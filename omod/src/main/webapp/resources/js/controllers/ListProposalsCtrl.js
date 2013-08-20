define([
    'angular',
    'config',
    'js/services/services',
    'js/services/menu',
    'js/services/alerts',
    './index'
  ],
  function(angular, config) {

    'use strict';

    angular.module('cpm.controllers').controller('ListProposalsCtrl',

      function($scope, $location, Proposals, Menu, Alerts) {

        document.title = 'Manage Concept Proposals';
        $scope.contextPath = config.contextPath;
        $scope.resourceLocation = config.resourceLocation;
        $scope.responseReceived = false;

        $scope.menu = Menu.getMenu(2);

        $scope.alerts = Alerts.dequeue();

        $scope.proposals = Proposals.query(function() {
          $scope.responseReceived = true;
        });

        $scope.editProposal = function(proposalId) {
          $location.path('/edit/' + proposalId);
        };
      }
    );
  }
);
