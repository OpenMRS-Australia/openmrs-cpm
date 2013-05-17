define(['angular', 'js/controllers/ListProposalsCtrl', 'js/controllers/EditProposalCtrl', 'js/controllers/SearchConceptsDialogCtrl', 'js/controllers/SettingsCtrl'], function(angular) {
  var appRoot = document.getElementById("cpmapp");
  appRoot.setAttribute("ng-app", "cpm");
  angular.element(appRoot).ready(function() {
	angular.bootstrap(appRoot, ["cpm"]);
  });
});
