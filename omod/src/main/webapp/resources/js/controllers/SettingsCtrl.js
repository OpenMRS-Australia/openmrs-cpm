define(['js/cpm', 'config'], function(cpm, config) {
  cpm.controller('SettingsCtrl', ['$scope', 'Settings', function($scope, Settings) {

    $scope.contextPath = config.contextPath;
    $scope.resourceLocation = config.resourceLocation;

    document.title = 'Manage Concept Proposal Settings';
    $scope.isLoading = true;

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
