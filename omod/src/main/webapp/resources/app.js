define(['angular', 'ListProposalsCtrl', 'EditProposalCtrl', 'SearchConceptsDialogCtrl', 'SettingsCtrl'], function(angular) {
  var appRoot = document.getElementById("cpmapp");
  appRoot.setAttribute("ng-app", "cpm");
  angular.element(appRoot).ready(function() {
    angular.bootstrap(appRoot, ["cpm"]);
  });
});
