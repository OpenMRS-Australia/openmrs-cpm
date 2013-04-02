define(['angular', 'ListIncomingProposalsCtrl', 'ReviewProposalCtrl', 'ReviewConceptCtrl', 'SearchConceptsDialogCtrlReview'], function(angular) {
  var appRoot = document.getElementById("cpmapp");
  appRoot.setAttribute("ng-app", "cpm-review");
  angular.element(appRoot).ready(function() {
    angular.bootstrap(appRoot, ["cpm-review"]);
  });
});
