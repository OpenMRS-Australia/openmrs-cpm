define(['./index', 'config'], function(controllers, config) {
  
  controllers.controller('SettingsCtrl', ['$scope', 'Settings', 'Menu', function($scope, Settings, MenuService) {

    $scope.contextPath = config.contextPath;
    $scope.resourceLocation = config.resourceLocation;

    document.title = 'Manage Concept Proposal Settings';
    $scope.isLoading = true;

    $scope.menu = MenuService.getMenu(4);

    $scope.settings = Settings.get(function() {
      $scope.isLoading = false;
    });

    $scope.save = function() {
      $scope.isLoading = true;
      $scope.settings.$save(function() {
        $scope.isLoading = false;
      });
    }
  }]);
});
