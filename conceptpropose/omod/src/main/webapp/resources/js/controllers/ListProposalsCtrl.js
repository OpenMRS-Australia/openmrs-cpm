define([
    'angular',
    'config',
    'js/services/proposals',
    'js/services/menu',
    'js/services/alerts',
    './index'
  ],
  function(angular, config) {

    'use strict';

    angular.module('conceptpropose.controllers').controller('ListProposalsCtrl',

      function($scope, $location, $window, Proposals, Menu, Alerts, $http, $route) {

        document.title = 'Manage Concept Proposals';
        $scope.contextPath = config.contextPath;
        $scope.resourceLocation = config.resourceLocation;
        $scope.responseReceived = false;

        $scope.isLoading = true;

        $scope.menu = Menu.getMenu(2);

        $scope.alerts = Alerts.dequeue();

        $scope.proposals = Proposals.query(function() {
          $scope.responseReceived = true;
          $scope.isLoading = false;
        });

        $scope.editProposal = function(proposalId) {
          $location.path('/edit/' + proposalId);
        };

        $scope.getStatuses = function() {
          var url = '/openmrs/ws/conceptpropose/proposalstatuses';
          $scope.isLoading = true;
          $http.get(url, {})
            .success(function(data) {
              console.log(data);
              if(data) {
                $window.alert('Update retrieved. Refreshing page...');
                $scope.proposals = data;
                $route.reload();
              } else {
                $window.alert('No data retrieved. Please try again');
              }
              $scope.isLoading = false;
            })
            .error(function(data) {
              var text = '';
              if(data.status === '500') {
                text = '';
              } else if(data.status === '401') {
                text = 'Unauthorized - you need to log in';
              } else {
                text = 'Unknown error: ' + data.status;
              }
              $window.alert('Error refreshing proposals ('+ text + ')');
              $scope.isLoading = false;
            });
        };
      }
    );
  }
);
