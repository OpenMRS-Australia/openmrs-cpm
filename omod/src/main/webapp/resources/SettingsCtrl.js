define(['cpm', 'config'], function(cpm, config) {
  cpm.controller('SettingsCtrl', ['$scope', 'Settings', function($scope, Settings) {
    document.title = 'Manage Concept Proposal Settings';
    $scope.responseReceived = false;
    $scope.settings = Settings.get(function() {
      $scope.responseReceived = true;
    });

    $scope.save = function() {
      $scope.settings.$save();
    }
  }]);
});
