define([
    'angular',
    'config',
    'js/services/settings',
    'js/services/menu',
    './index'
  ],
  function(angular, config, $http) {

    'use strict';
  
    angular.module('conceptpropose.controllers').controller('SettingsCtrl',
      function($scope, $http, Settings, Menu) {

        $scope.contextPath = config.contextPath;
        $scope.resourceLocation = config.resourceLocation;

        document.title = 'Manage Concept Proposal Settings';
        $scope.isLoading = true;
        $scope.isTesting = false;
        $scope.settingsValid = false;
        $scope.settingsTested = false;
        $scope.connectErrorMessage = "";

        $scope.menu = Menu.getMenu(3);

        $scope.settings = Settings.get(function() {
          $scope.isLoading = false;
        });

        $scope.testConnection=function(){
            $scope.isTesting = true;
            $http.post(config.contextPath + '/ws/conceptpropose/settings/connectionResult', $scope.settings)
                 .success(
                    function(result){
                       $scope.settingsValid = (result == "Success");
                       $scope.connectErrorMessage = result;
                       $scope.settingsTested = true;
                       $scope.isTesting = false;
                  });
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