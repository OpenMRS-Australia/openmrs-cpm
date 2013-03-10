define(['cpm', 'config'], function(cpm, config) {
  cpm.controller('SettingsCtrl', ['$scope', 'Settings', function($scope, Settings) {

    document.title = 'Manage Concept Proposal Settings';
    $scope.saving = false;

    $scope.responseReceived = false;
    $scope.settings = Settings.get(function() {
      $scope.responseReceived = true;
    });

    $scope.save = function() {
      $scope.saving = true;
      $scope.settings.$save(function() {
        $scope.saving = false;
      });
    }
  }]);
});
