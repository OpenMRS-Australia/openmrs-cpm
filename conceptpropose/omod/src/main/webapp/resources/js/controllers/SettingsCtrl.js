define([
    'angular',
    'config',
    'js/services/settings',
    'js/services/menu',
    './index'
  ],
  function(angular, config) {

    'use strict';
  
    angular.module('conceptpropose.controllers').controller('SettingsCtrl',
      function($scope, $http, Settings, Menu) {

        $scope.contextPath = config.contextPath;
        $scope.resourceLocation = config.resourceLocation;

        document.title = 'Manage Concept Proposal Settings';
        $scope.isLoading = true;
        $scope.settingsValid = false;
        $scope.connectErrorMessage = '';

        $scope.menu = Menu.getMenu(3);

        $scope.settings = Settings.get(function() {
          $scope.isLoading = false;
        });

        $scope.testConnection = function () {

          var responseHandler = function (result) {
            $scope.settingsValid = (result === 'Success');
            $scope.connectErrorMessage = typeof result === 'string' ? result : 'Unknown Error';
            $scope.isLoading = false;
          };

          $scope.isLoading = true;
          $http.post(config.contextPath + '/ws/conceptpropose/settings/connectionResult', $scope.settings)
            .success(responseHandler).error(responseHandler);
        };

        $scope.save = function() {
          $scope.isLoading = true;
          $scope.settings.$save(function() {
            $scope.isLoading = false;
          });
        };
      }
    );
  }
);