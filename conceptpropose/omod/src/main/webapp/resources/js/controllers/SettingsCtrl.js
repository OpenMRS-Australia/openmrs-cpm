define([
    'angular',
    'config',
    './index'
  ],
  function(angular, config) {

    'use strict';
  
    angular.module('cpm.controllers').controller('SettingsCtrl',
      function($scope, Settings, Menu) {

        $scope.contextPath = config.contextPath;
        $scope.resourceLocation = config.resourceLocation;

        document.title = 'Manage Concept Proposal Settings';
        $scope.isLoading = true;

        $scope.menu = Menu.getMenu(3);

        $scope.settings = Settings.get(function() {
          $scope.isLoading = false;
        });

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